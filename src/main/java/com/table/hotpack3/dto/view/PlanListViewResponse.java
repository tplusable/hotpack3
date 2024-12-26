package com.table.hotpack3.dto.view;

import com.table.hotpack3.domain.Plan;
import lombok.Getter;

@Getter
public class PlanListViewResponse {

    private final Long planId;
    private final String planTitle;
    private final String planContent;

    public PlanListViewResponse(Plan plan) {
        this.planId=plan.getPlanId();
        this.planTitle=plan.getPlanTitle();
        this.planContent=plan.getPlanContent();
    }
}
