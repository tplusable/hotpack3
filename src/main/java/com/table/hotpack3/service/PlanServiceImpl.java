package com.table.hotpack3.service;

import com.table.hotpack3.domain.Plan;
import com.table.hotpack3.dto.request.AddPlanRequest;
import com.table.hotpack3.dto.request.UpdatePlanRequest;
import com.table.hotpack3.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;

    @Override
    public Plan save(AddPlanRequest request) {
        return planRepository.save(request.toEntity());
    }

    @Override
    public List<Plan> findAll() {
        return planRepository.findAll();
    }

    @Override
    public Plan findById(Long planId) {
        return planRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("not found: "+planId));
    }

    @Override
    public void delete(Long planId) {
        planRepository.deleteById(planId);
    }

    @Transactional
    @Override
    public Plan update(Long planId, UpdatePlanRequest request) {
        Plan plan=planRepository.findById(planId)
                .orElseThrow(()->new IllegalArgumentException("not found: "+planId));

        plan.update(request.getPlanTitle(), request.getPlanContent());

        return plan;
    }
}
