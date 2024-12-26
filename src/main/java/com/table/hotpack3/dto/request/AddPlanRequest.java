package com.table.hotpack3.dto.request;

import com.table.hotpack3.domain.Plan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddPlanRequest {

    private String planTitle;
    private String planContent;

    public Plan toEntity() {
        return Plan.builder()
                .planTitle(planTitle)
                .planContent(planContent)
                .build();
    }


}
