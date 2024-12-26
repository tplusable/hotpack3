package com.table.hotpack3.controller.api;

import com.table.hotpack3.domain.Plan;
import com.table.hotpack3.dto.request.AddPlanRequest;
import com.table.hotpack3.dto.response.PlanResponse;
import com.table.hotpack3.dto.request.UpdatePlanRequest;
import com.table.hotpack3.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PlanApiController {

    private final PlanService planService;

    @PostMapping("/api/plans")
    public ResponseEntity<Plan> addPlan(@RequestBody AddPlanRequest request) {
        Plan savedPlan= planService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedPlan);
    }

    @GetMapping("/api/plans")
    public ResponseEntity<List<PlanResponse>> findAllPlans() {
        List<PlanResponse> plans=planService.findAll()
                .stream()
                .map(PlanResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(plans);
    }

    @GetMapping("/api/plans/{id}")
    public ResponseEntity<PlanResponse> findPlans(@PathVariable("id") Long planId) {
        Plan plan=planService.findById(planId);

        return ResponseEntity.ok()
                .body(new PlanResponse(plan));
    }

    @DeleteMapping("/api/plans/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable("id") Long planId) {
        planService.delete(planId);

        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/api/plans/{id}")
    public ResponseEntity<Plan> updatePlan(@PathVariable("id") Long planId, @RequestBody UpdatePlanRequest request) {
        Plan updatePlan=planService.update(planId, request);

        return ResponseEntity.ok()
                .body(updatePlan);
    }
}
