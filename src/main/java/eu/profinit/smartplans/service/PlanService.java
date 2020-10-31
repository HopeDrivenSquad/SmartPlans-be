package eu.profinit.smartplans.service;

import eu.profinit.smartplans.model.Plan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PlanService {

    public List<Plan> getPlans(LocalDate date, long _balance) {
        var plans= loadPlans();

        BigDecimal avgDailyRevenue = getAvgDailyRevenue();
        BigDecimal avgDailyCosts = getAvgDailyCosts();
        BigDecimal profit = avgDailyRevenue.subtract(avgDailyCosts);
        log.info("Daily revenue {}, costs {}, profit {}", avgDailyRevenue, avgDailyCosts, profit);
        BigDecimal balance = new BigDecimal(_balance);

        BigDecimal sumDailySavingAmount = BigDecimal.ZERO;
        for (Plan plan : plans) {
            if (!plan.getEnabled()) {
                continue;
            }
            int days = (int) ChronoUnit.DAYS.between(date, plan.getDateTo());
            plan.setDaysToEnd(days);
            plan.setDailySavingAmount(plan.getAmount().divide(new BigDecimal(days), 2, RoundingMode.HALF_UP));
            sumDailySavingAmount = sumDailySavingAmount.add(plan.getDailySavingAmount());
            log.info("Plan {}", plan);
        }

        for (Plan plan : plans) {
            if (!plan.getEnabled()) {
                continue;
            }
            BigDecimal b = balance.multiply(plan.getDailySavingAmount()).divide(sumDailySavingAmount, 2, RoundingMode.HALF_UP);
            plan.setPercentages(b.multiply(new BigDecimal(100)).divide(plan.getAmount(), 0, RoundingMode.HALF_UP).intValue());
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


        return plans;
    }

    public List<Plan> loadPlans() {
        var plans = new ArrayList<Plan>();

        plans.add(new Plan(1L,  true, "Rezerva", null, new BigDecimal("30000"), LocalDate.of(2021, 6, 30), null, null, null));
        plans.add(new Plan(2L,  true, "Dovolená", null, new BigDecimal("15000"), LocalDate.of(2021, 6, 30), null, null, null));
        plans.add(new Plan(3L,  true, "Dárky vánoce", null, new BigDecimal("5000"), LocalDate.of(2020, 12, 1), null, null, null));
        plans.add(new Plan(4L,  true, "Spolufinancování hypo", null, new BigDecimal("400000"), LocalDate.of(2021, 6, 30), null, null, null));

        return plans;
    }

    public BigDecimal getAvgDailyRevenue() {
        return new BigDecimal("30000").divide(new BigDecimal(30), 2, RoundingMode.HALF_UP);
    }

    public BigDecimal getAvgDailyCosts() {
        return new BigDecimal("15000").divide(new BigDecimal(30), 2, RoundingMode.HALF_UP);
    }

    public BigDecimal getAccountBalance() {
        return new BigDecimal("100000");
    }

}
