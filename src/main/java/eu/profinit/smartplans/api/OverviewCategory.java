package eu.profinit.smartplans.api;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OverviewCategory {

    String name;
    BigDecimal amountSavedPerMonth;
    List<TransactionApi> transactions;
}
