package com.table.hotpack3.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.table.hotpack3.domain.Plan;
import com.table.hotpack3.dto.request.AddPlanRequest;
import com.table.hotpack3.dto.request.UpdatePlanRequest;
import com.table.hotpack3.repository.PlanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PlanApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    PlanRepository planRepository;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc= MockMvcBuilders.webAppContextSetup(context)
                .build();
        planRepository.deleteAll();
    }

    @DisplayName("addPlan: 여행계획 글 추가에 성공")
    @Test
    public void addPlan() throws Exception {
        final String url = "/api/plans";
        final String plantTitle= "글삽입테스트";
        final String planContent="글삽입테스트";
        final AddPlanRequest userRequest= new AddPlanRequest(plantTitle, planContent);

        final String requestBody=objectMapper.writeValueAsString(userRequest);

        ResultActions result=mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        result.andExpect(status().isCreated());

        List<Plan> plans=planRepository.findAll();

        assertThat(plans.size()).isEqualTo(1);
        assertThat(plans.get(0).getPlanTitle()).isEqualTo(plantTitle);
        assertThat(plans.get(0).getPlanContent()).isEqualTo(planContent);
    }

    @DisplayName("findAllPlans: 여행계획 글 목록 조회에 성공")
    @Test
    public void findAllPlans() throws Exception{
        final String url="/api/plans";
        final String planTitle="글목록테스트";
        final String planContent="글목록테스트";

        planRepository.save(Plan.builder()
                .planTitle(planTitle)
                .planContent(planContent)
                .build());

        final ResultActions resultActions=mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].planContent").value(planContent))
                .andExpect(jsonPath("$[0].planTitle").value(planTitle));

    }

    @DisplayName("findPlan: 여행계획 글 조회에 성공")
    @Test
    public void findPlan() throws Exception {

        final String url ="/api/plans/{id}";
        final String planTitle="글조회테스트";
        final String planContent="글조회테스트";

        Plan savedPlan=planRepository.save(Plan.builder()
                .planTitle(planTitle)
                .planContent(planContent)
                .build());

        final ResultActions resultActions=mockMvc.perform(get(url, savedPlan.getPlanId()));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.planContent").value(planContent))
                .andExpect(jsonPath("$.planTitle").value(planTitle));
    }

    @DisplayName("deletePlan: 여행 계획 글 삭제에 성공")
    @Test
    public void deletePlan() throws Exception {
        final String url="/api/plans/{id}";
        final String planTitle="글삭제테스트";
        final String planContent="글삭제테스트";

        Plan savedPlan=planRepository.save(Plan.builder()
                .planTitle(planTitle)
                .planContent(planContent)
                .build());

        mockMvc.perform(delete(url, savedPlan.getPlanId()))
                .andExpect(status().isOk());

        List<Plan> plans=planRepository.findAll();

        assertThat(plans).isEmpty();
    }

    @DisplayName("updatePlan: 여행계획 글 수정에 성공")
    @Test
    public void updatePlan() throws Exception {
        final String url="/api/plans/{id}";
        final String planTitle="planTitle";
        final String planContent="planContent";

        Plan savedPlan=planRepository.save(Plan.builder()
                .planTitle(planTitle)
                .planContent(planContent)
                .build());

        final String newTitle="글수정테스트";
        final String newContent="글수정테스트";

        UpdatePlanRequest request= new UpdatePlanRequest(newTitle, newContent);

        ResultActions result=mockMvc.perform(put(url, savedPlan.getPlanId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));

        result.andExpect(status().isOk());

        Plan plan=planRepository.findById(savedPlan.getPlanId()).get();

        assertThat(plan.getPlanTitle()).isEqualTo(newTitle);
        assertThat(plan.getPlanContent()).isEqualTo(newContent);
    }
}