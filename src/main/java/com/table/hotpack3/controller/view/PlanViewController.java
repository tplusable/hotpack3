package com.table.hotpack3.controller.view;

import com.table.hotpack3.domain.Plan;
import com.table.hotpack3.dto.view.PlanListViewResponse;
import com.table.hotpack3.dto.view.PlanViewResponse;
import com.table.hotpack3.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class PlanViewController {

    private final PlanService planService;

    @GetMapping("/plans")
    public String getPlans(Model model) {
        List<PlanListViewResponse> plans = planService.findAll().stream()
                .map(PlanListViewResponse::new)
                .toList();
        model.addAttribute("plans", plans);

        return "planList";
    }

    @GetMapping("/plans/{id}")
    public String getPlans(@PathVariable("id") Long planId, Model model) {
        Plan plan= planService.findById(planId);
        model.addAttribute("plan", new PlanViewResponse(plan));

        return "plan";
    }

    @GetMapping("/new-plan")
    public String  newPlan(@RequestParam(required=false) Long id, Model model) {
        if (id==null) {
            model.addAttribute("plan", new PlanViewResponse());
        } else {
            Plan plan= planService.findById(id);
            model.addAttribute("plan", new PlanViewResponse(plan));
        }
        return "newPlan";
    }
}
