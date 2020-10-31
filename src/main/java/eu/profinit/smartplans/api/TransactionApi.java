package eu.profinit.smartplans.api;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TransactionApi {

    Long transactionId;
    BigDecimal amount;
    String merchantCategory;
    List<String> tags;
}
