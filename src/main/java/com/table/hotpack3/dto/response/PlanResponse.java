package com.table.hotpack3.dto.response;

import com.table.hotpack3.domain.Plan;
import lombok.Getter;

@Getter
public class PlanResponse {

    private final String planTitle;
    private final String planContent;

    public PlanResponse(Plan plan) {
        this.planTitle= plan.getPlanTitle();
        this.planContent= plan.getPlanContent();
    }
}
