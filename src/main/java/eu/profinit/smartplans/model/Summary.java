package eu.profinit.smartplans.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Summary {

    // kolik ušetří
    private BigDecimal amountSavedPerMonth;
    // plánované výdaje costs
    private BigDecimal amountPlanPerMonth;
    // kolik mám možnost utratit (saved - plan)
    private BigDecimal amountTotalPerMonth;

    private BigDecimal emergencyBalance;

}
