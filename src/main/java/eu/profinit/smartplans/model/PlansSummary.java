package eu.profinit.smartplans.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlansSummary {

    private Summary summary;
    private List<Plan> plans;

}
