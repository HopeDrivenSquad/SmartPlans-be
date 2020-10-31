package eu.profinit.smartplans.controller;

import eu.profinit.smartplans.model.Plan;
import eu.profinit.smartplans.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/plans")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    @GetMapping
    public List<Plan> getPlans(@RequestParam long balance) {
        var plans = planService.getPlans(LocalDate.now(), balance);


        return plans;
    }

}
