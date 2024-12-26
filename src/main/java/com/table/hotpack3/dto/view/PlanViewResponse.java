package com.table.hotpack3.dto.view;

import com.table.hotpack3.domain.Plan;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class PlanViewResponse {
    private Long planId;
    private String planTitle;
    private String planContent;
    private LocalDateTime regdate;

    public PlanViewResponse(Plan plan) {
        this.planId= plan.getPlanId();
        this.planTitle= plan.getPlanTitle();
        this.planContent= plan.getPlanContent();
        this.regdate= plan.getRegdate();
    }
}
