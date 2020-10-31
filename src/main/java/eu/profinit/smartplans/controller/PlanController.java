package eu.profinit.smartplans.controller;

import eu.profinit.smartplans.model.Plan;
import eu.profinit.smartplans.model.PlansSummary;
import eu.profinit.smartplans.model.Summary;
import eu.profinit.smartplans.service.PlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/plans")
@RequiredArgsConstructor
@Slf4j
public class PlanController {

    private final PlanService planService;

    @GetMapping
    public PlansSummary getPlans(@RequestParam String clientId, @RequestParam long currentBalance) {
        var plans = planService.getPlans(LocalDate.now(), clientId, currentBalance);
        var summary = new Summary();
        var plansSummary = new PlansSummary(summary, plans);

        return plansSummary;
    }

    @PostMapping
    public Plan createPlan(@RequestParam String clientId, @RequestBody Plan plan) {
        log.info("create plan {}", plan);
        return planService.insert(clientId, plan);
    }

    @PutMapping("/{id}")
    public Plan savePlan(@PathVariable("id") int id, @RequestBody Plan plan) {
        log.info("update plan id {}, {}", id, plan);
        plan.setId(id);
        return planService.update(plan);
    }

    @DeleteMapping("/{id}")
    public void deletePlan(@PathVariable("id") int id) {
        log.info("delete plan id {}", id);
        planService.delete(id);
    }

}
