package eu.profinit.smartplans.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class trx {

    private String name;
    private String category;
    private BigDecimal amount;

}
