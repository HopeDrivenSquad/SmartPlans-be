package eu.profinit.smartplans.service;

import eu.profinit.smartplans.api.OverviewCategory;
import eu.profinit.smartplans.api.OverviewSummary;
import eu.profinit.smartplans.api.TransactionApi;
import eu.profinit.smartplans.api.TransactionOverview;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;

import static eu.profinit.smartplans.db.Tables.TAG;
import static eu.profinit.smartplans.db.Tables.TRANSACTION;
import static java.util.Objects.isNull;
import static org.jooq.impl.DSL.*;

@Service
public class TransactionService {

    public static final int MONTHS_HISTORY = 3;

    private final DSLContext dsl;

    @Autowired
    public TransactionService(DSLContext dsl) {
        this.dsl = dsl;
    }

    public TransactionApi getTransaction(Long id) {
        var fetch = dsl
                .select(TRANSACTION.TRANSACTION_ID,
                        TRANSACTION.AMOUNT,
                        TRANSACTION.TX_DATE,
                        TRANSACTION.MERCHANT_CATEGORY,
                        TRANSACTION.TAG_ARRAY)
                .from(TRANSACTION)
                .where(TRANSACTION.TRANSACTION_ID.equal(id))
                .fetch();

        var transactionApi = new TransactionApi();
        transactionApi.setTransactionId(fetch.getValue(0, TRANSACTION.TRANSACTION_ID));
        transactionApi.setAmount(fetch.getValue(0, TRANSACTION.AMOUNT));
        transactionApi.setTransactionDate(fetch.getValue(0, TRANSACTION.TX_DATE));
        transactionApi.setMerchantCategory(fetch.getValue(0, TRANSACTION.MERCHANT_CATEGORY));
        transactionApi.setTags(Arrays.asList(fetch.getValue(0, TRANSACTION.TAG_ARRAY)));

        return transactionApi;
    }

    public TransactionOverview getOverview(Long clientId) {

        final TransactionOverview transactionOverview = new TransactionOverview();
        transactionOverview.setCategories(new ArrayList<>());

        var taggedTransactions = dsl.select(TRANSACTION.TRANSACTION_ID, TRANSACTION.AMOUNT, TRANSACTION.TX_DATE, field(name("tag_name"), String.class))
                .from(TRANSACTION, lateral(unnest(TRANSACTION.TAG_ARRAY).as("t", "tag_name")))
                .where(TRANSACTION.CLIENT_ID.eq(clientId.intValue())
                        .and(TRANSACTION.AMOUNT.le(BigDecimal.ZERO))
                        .and(TRANSACTION.TX_DATE.ge(LocalDate.now().minus(MONTHS_HISTORY, ChronoUnit.MONTHS))));

        var results = dsl.select(
                sum(taggedTransactions.field(TRANSACTION.AMOUNT)).div(MONTHS_HISTORY),
                listAgg(taggedTransactions.field(TRANSACTION.TRANSACTION_ID), ",").withinGroupOrderBy(taggedTransactions.field(TRANSACTION.TRANSACTION_ID)),
                TAG.TAG_NAME,
                TAG.NECESSITY)
                .from(taggedTransactions)
                .innerJoin(TAG).on(TAG.TAG_NAME.equal(taggedTransactions.field("tag_name", String.class)))
                .where(TAG.NECESSITY.le(BigDecimal.valueOf(0.9)))
                .groupBy(TAG.TAG_NAME, TAG.NECESSITY)
                .fetch();

        results.forEach(r -> {
            final OverviewCategory overviewCategory = new OverviewCategory();
            overviewCategory.setAmountSavedPerMonth(r.get(0, BigDecimal.class));
            overviewCategory.setName(r.get(2, String.class));

            final String txIds = r.get(1, String.class);
            if (!StringUtils.isEmpty(txIds)) {
                overviewCategory.setTransactions(new ArrayList<>());
                final String[] ids = txIds.split(",");
                for (String id : ids) {
                    final TransactionApi transaction = getTransaction(Long.valueOf(id));
                    overviewCategory.getTransactions().add(transaction);
                }
            }

            transactionOverview.getCategories().add(overviewCategory);
        });

        OverviewSummary overviewSummary = new OverviewSummary();
        final BigDecimal overallSaved = transactionOverview.getCategories().stream()
                .map(OverviewCategory::getAmountSavedPerMonth)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        overviewSummary.setAmountSavedPerMonth(overallSaved);
        transactionOverview.setSummary(overviewSummary);

        return transactionOverview;
    }

    BigDecimal getThreeMonthSpending(Long clientId) {
        final BigDecimal bigDecimal = dsl.select(sum(TRANSACTION.AMOUNT))
                .from(TRANSACTION)
                .where(TRANSACTION.CLIENT_ID.eq(clientId.intValue())
                        .and(TRANSACTION.AMOUNT.le(BigDecimal.ZERO))
                        .and(TRANSACTION.TX_DATE.ge(LocalDate.now().minus(MONTHS_HISTORY, ChronoUnit.MONTHS))))
                .fetch().get(0).get(0, BigDecimal.class);
        return isNull(bigDecimal) ? BigDecimal.ZERO : bigDecimal.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP).multiply(new BigDecimal(-1));
    }

    BigDecimal getThreeMonthEarnings(Long clientId) {
        final BigDecimal bigDecimal = dsl.select(sum(TRANSACTION.AMOUNT))
                .from(TRANSACTION)
                .where(TRANSACTION.CLIENT_ID.eq(clientId.intValue())
                        .and(TRANSACTION.AMOUNT.ge(BigDecimal.ZERO))
                        .and(TRANSACTION.TX_DATE.ge(LocalDate.now().minus(MONTHS_HISTORY, ChronoUnit.MONTHS))))
                .fetch().get(0).get(0, BigDecimal.class);
        return isNull(bigDecimal) ? BigDecimal.ZERO : bigDecimal.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
    }
}
