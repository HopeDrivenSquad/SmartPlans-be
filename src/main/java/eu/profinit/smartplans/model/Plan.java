package eu.profinit.smartplans.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Plan {

    private Long id;
    private Boolean enabled;
    private String name;
    private Integer percentages;
    private BigDecimal amount;
    private LocalDate dateTo;
    private Boolean isOk;

    private Integer daysToEnd;
    private BigDecimal dailySavingAmount;

}
