package com.a702.hup.application.contorller;

import com.a702.hup.application.data.request.AgendaCreateRequest;
import com.a702.hup.application.data.request.AgendaUpdateRequest;
import com.a702.hup.application.facade.AgendaFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(AgendaController.class)
@AutoConfigureRestDocs
@WithMockUser
class AgendaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AgendaFacade agendaFacade;

    @Test
    void agendaSaveTestWhenSuccess() throws Exception {
        AgendaCreateRequest request = new AgendaCreateRequest(1, "의사결정 제공");

        mockMvc.perform(RestDocumentationRequestBuilders
                        .post("/agenda").with(csrf())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(document("save-agenda",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("issueId").type(JsonFieldType.NUMBER).description("이슈 Id"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("의사결정 내용")
                        )
                ));
    }

    @Test
    void SaveAgendaAssigneeTestWhenSuccess() throws Exception {
        Map<String, Integer> request = new HashMap<>();
        request.put("agendaId", 1);
        request.put("memberId", 1);

        mockMvc.perform(RestDocumentationRequestBuilders
                        .post("/agenda/assignee").with(csrf())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(document("save-agenda-assignee",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("agendaId").type(JsonFieldType.NUMBER).description("의사결정 Id"),
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("담당자 Id")
                        )
                ));
    }

    @Test
    void updateAgendaTestWhenSuccess() throws Exception {
        AgendaUpdateRequest request = new AgendaUpdateRequest(1, "수정된 내용", "완료");

        mockMvc.perform(RestDocumentationRequestBuilders
                        .put("/agenda").with(csrf())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("update-agenda-assignee",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("agendaId").type(JsonFieldType.NUMBER).description("의사결정 Id"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("수정할 내용"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("상태 변경,[ASSIGNED,PROGRESS,COMPLETED,APPROVED]")
                        )
                ));
    }

    @Test
    void deleteAgendaTestWhenSuccess() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders
                        .delete("/agenda/{agendaId}",1)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("delete-agenda",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    void deleteAssigneeTestWhenSuccess() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders
                        .delete("/agenda/assignee/{assigneeId}",1)
                        .param("assignId","1").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("delete-agenda-assignee",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

}