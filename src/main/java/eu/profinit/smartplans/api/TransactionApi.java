package eu.profinit.smartplans.api;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionApi {

    Long transactionId;
    BigDecimal amount;
    String category;
}
