package eu.profinit.smartplans.service;

import eu.profinit.smartplans.api.TransactionApi;
import org.jooq.DSLContext;
import org.jooq.Record5;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static eu.profinit.smartplans.db.Tables.TRANSACTION;

@Service
public class TransactionService {

    private final DSLContext dsl;

    @Autowired
    public TransactionService(DSLContext dsl) {
        this.dsl = dsl;
    }

    public TransactionApi getTransaction(Long id) {
        final Result<Record5<Long, BigDecimal, LocalDate, String, String[]>> fetch = dsl
                .select(TRANSACTION.TRANSACTION_ID,
                        TRANSACTION.AMOUNT,
                        TRANSACTION.TX_DATE,
                        TRANSACTION.MERCHANT_CATEGORY,
                        TRANSACTION.TAG_ARRAY)
                .from(TRANSACTION)
                .where(TRANSACTION.TRANSACTION_ID.equal(id))
                .fetch();

        final TransactionApi transactionApi = new TransactionApi();
        transactionApi.setTransactionId(fetch.getValue(0, TRANSACTION.TRANSACTION_ID));
        transactionApi.setAmount(fetch.getValue(0, TRANSACTION.AMOUNT));
        transactionApi.setTransactionDate(fetch.getValue(0, TRANSACTION.TX_DATE));
        transactionApi.setMerchantCategory(fetch.getValue(0, TRANSACTION.MERCHANT_CATEGORY));
        transactionApi.setTags(Arrays.asList(fetch.getValue(0, TRANSACTION.TAG_ARRAY)));

        return transactionApi;
    }
}
