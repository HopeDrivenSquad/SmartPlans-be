package eu.profinit.smartplans.api;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class TransactionApi {

    Long transactionId;
    BigDecimal amount;
    LocalDate transactionDate;
    String merchantCategory;
    List<String> tags;
}
