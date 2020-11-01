package eu.profinit.smartplans.service;

import eu.profinit.smartplans.db.tables.records.PlanRecord;
import eu.profinit.smartplans.model.Plan;
import eu.profinit.smartplans.model.PlansSummary;
import eu.profinit.smartplans.model.Summary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static eu.profinit.smartplans.db.Tables.PLAN;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlanService {
    private final DSLContext dsl;
    private final TransactionService transactionService;

    public PlansSummary getPlansSummary(LocalDate date, String clientId, long _balance) {
        var summary = new Summary();
        BigDecimal threeMonthEarnings = transactionService.getThreeMonthEarnings(Long.valueOf(clientId));
        BigDecimal threeMonthSpending = transactionService.getThreeMonthSpending(Long.valueOf(clientId));
        summary.setEmergencyBalance(threeMonthSpending);
        summary.setAmountSavedPerMonth(threeMonthEarnings.divide(new BigDecimal(3), 2, RoundingMode.HALF_UP));
        summary.setAmountPlanPerMonth(threeMonthSpending.divide(new BigDecimal(3), 2, RoundingMode.HALF_UP));
        summary.setAmountTotalPerMonth(summary.getAmountSavedPerMonth().subtract(summary.getAmountPlanPerMonth()));

        var plans= loadPlans(clientId);
        PlansSummary plansSummary = new PlansSummary(summary, plans);
        var enabledPlans = plans.stream()
                .filter(Plan::getEnabled)
                .collect(Collectors.toList());

        BigDecimal avgDailyRevenue = threeMonthEarnings.divide(new BigDecimal(90), 2, RoundingMode.HALF_UP);
        BigDecimal avgDailyCosts = threeMonthSpending.divide(new BigDecimal(90), 2, RoundingMode.HALF_UP);
        BigDecimal profit = avgDailyRevenue.subtract(avgDailyCosts);
        log.info("Daily revenue {}, costs {}, profit {}", avgDailyRevenue, avgDailyCosts, profit);
        BigDecimal balance = new BigDecimal(_balance).subtract(summary.getEmergencyBalance());
        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            balance = BigDecimal.ZERO;
        }

        BigDecimal sumDailySavingAmount = BigDecimal.ZERO;
        for (Plan plan : enabledPlans) {
            int days = (int) ChronoUnit.DAYS.between(date, plan.getDateTo());
            plan.setDaysToEnd(days);
            plan.setDailySavingAmount(plan.getAmount().divide(new BigDecimal(days), 2, RoundingMode.HALF_UP));
            sumDailySavingAmount = sumDailySavingAmount.add(plan.getDailySavingAmount());
            log.info("Plan {}", plan);
        }

        recountPercentage(enabledPlans, balance, sumDailySavingAmount);

        var plansToRecount = new ArrayList<Plan>();
        sumDailySavingAmount = BigDecimal.ZERO;
        BigDecimal balanceWithoutSuccessPlans = balance;
        boolean recount = false;
        for (Plan plan : enabledPlans) {
            if (plan.getPercentages() <= 100) {
                sumDailySavingAmount = sumDailySavingAmount.add(plan.getDailySavingAmount());
                plansToRecount.add(plan);
                continue;
            }
            recount = true;
            balanceWithoutSuccessPlans = balanceWithoutSuccessPlans.subtract(plan.getAmount());
            plan.setPercentages(100);
            log.info("Plan is over 100% {}", plan);
        }
        if (recount) {
            log.info("Recounting percentage");
            recountPercentage(plansToRecount, balanceWithoutSuccessPlans, sumDailySavingAmount);
        }

        BigDecimal availableProfit = profit;
        log.info("Avaiable profit {}", availableProfit);
        for (Plan plan : plans) {
            if (!plan.getEnabled()) {
                continue;
            }
            BigDecimal haveTosaving = plan.getDailySavingAmount().multiply(new BigDecimal(100-plan.getPercentages())).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
            plan.setIsOk(availableProfit.compareTo(haveTosaving) > 0);
            if (plan.getIsOk()) {
                availableProfit = availableProfit.subtract(haveTosaving);
            }
            log.info("AvailableProfit {} haveTosaving {} After count plan {}", availableProfit, haveTosaving, plan);
        }

        return plansSummary;
    }

    private void recountPercentage(List<Plan> enabledPlans, BigDecimal balance, BigDecimal sumDailySavingAmount) {
        log.info("Count percentage (balance {}, sumDailySavingAmount {})", balance, sumDailySavingAmount);
        for (Plan plan : enabledPlans) {
            BigDecimal b = balance.multiply(plan.getDailySavingAmount()).divide(sumDailySavingAmount, 2, RoundingMode.HALF_UP);
            plan.setPercentages(b.multiply(new BigDecimal(100)).divide(plan.getAmount(), 0, RoundingMode.HALF_UP).intValue());
            log.info("Plan {}", plan);
        }
    }

    public List<Plan> loadPlans(String clientId) {
        final Map<Integer, PlanRecord> integerPlanRecordMap = dsl
                .selectFrom(PLAN)
                .where(PLAN.CLIENT_ID.equal(Integer.valueOf(clientId))) // TODO validace na integer
                .fetch().intoMap(PLAN.ID);

        var plans = new ArrayList<Plan>();
        integerPlanRecordMap.values().forEach(pr -> plans.add(convertRecord(pr)));

        return plans;
    }

    private Plan convertRecord(PlanRecord pr) {
        var plan = new Plan();
        plan.setId(pr.getId());
        plan.setAmount(pr.getAmount());
        plan.setDateTo(pr.getDateTo());
        plan.setName(pr.getName());
        // FIXME: bad mapping - return null when is false in db
        plan.setEnabled(pr.getEnabled() != null ? pr.getEnabled() : false);

        return plan;
    }

    public Plan insert(String clientId, Plan _plan) {
        PlanRecord pr = dsl.insertInto(PLAN)
                .set(PLAN.CLIENT_ID, Integer.valueOf(clientId))
                .set(PLAN.AMOUNT, _plan.getAmount())
                .set(PLAN.DATE_TO, _plan.getDateTo())
                .set(PLAN.ENABLED, _plan.getEnabled())
                .set(PLAN.NAME, _plan.getName())
                .returning()
                .fetchOne();

        Plan plan = convertRecord(pr);

        return plan;
    }

    public Plan update(Plan _plan) {
        dsl.update(PLAN)
                .set(PLAN.ENABLED, _plan.getEnabled())
                .where(PLAN.ID.equal(_plan.getId()))
                .execute();

        PlanRecord pr = dsl.selectFrom(PLAN)
            .where(PLAN.ID.equal(_plan.getId()))
            .fetchOne();

        Plan plan = convertRecord(pr);

        return plan;
    }

    public void delete(int id) {
        dsl.delete(PLAN)
                .where(PLAN.ID.equal(id))
                .execute();
    }
}
