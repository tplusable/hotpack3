package com.table.hotpack3.service;

import com.table.hotpack3.domain.Plan;
import com.table.hotpack3.dto.request.AddPlanRequest;
import com.table.hotpack3.dto.request.UpdatePlanRequest;

import java.util.List;

public interface PlanService {

    Plan save(AddPlanRequest request);
    List<Plan> findAll();
    Plan findById(Long planId);
    void delete(Long id);
    Plan update(Long planId, UpdatePlanRequest request);
}
