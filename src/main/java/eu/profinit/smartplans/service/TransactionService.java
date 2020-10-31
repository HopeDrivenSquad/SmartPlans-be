package eu.profinit.smartplans.service;

import eu.profinit.smartplans.api.TransactionApi;
import org.jooq.DSLContext;
import org.jooq.Record4;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static eu.profinit.smartplans.db.Tables.TRANSACTION;

@Service
public class TransactionService {

    private DSLContext dsl;

    @Autowired
    public TransactionService(DSLContext dsl) {
        this.dsl = dsl;
    }

    public TransactionApi getTransaction(Long id) {
        final Result<Record4<Long, BigDecimal, String, String>> result = dsl
                .select(TRANSACTION.TRANSACTION_ID,
                        TRANSACTION.AMOUNT,
                        TRANSACTION.MERCHANT_CATEGORY,
                        TRANSACTION.MERCHANT_CATEGORY_ID)
                .from(TRANSACTION)
                .where(TRANSACTION.TRANSACTION_ID.equal(id))
                .fetch();

        final TransactionApi transactionApi = new TransactionApi();
        transactionApi.setTransactionId(result.getValue(0, TRANSACTION.TRANSACTION_ID));
        transactionApi.setAmount(result.getValue(0, TRANSACTION.AMOUNT));
        transactionApi.setCategory(result.getValue(0, TRANSACTION.MERCHANT_CATEGORY));

        return transactionApi;
    }
}
