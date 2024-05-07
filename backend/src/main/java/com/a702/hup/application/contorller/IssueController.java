package com.a702.hup.application.contorller;

import com.a702.hup.application.data.request.IssueSaveRequest;
import com.a702.hup.application.data.response.IssueDetailsResponse;
import com.a702.hup.application.data.response.IssueListByStatusResponse;
import com.a702.hup.application.facade.IssueFacade;
import com.a702.hup.domain.issue.IssueService;
import com.a702.hup.global.config.security.SecurityUserDetailsDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/issue")
public class IssueController {
    private final IssueFacade issueFacade;
    private final IssueService issueService;

    @GetMapping("/list")
    public ResponseEntity<IssueListByStatusResponse> findByProjectId(@PathVariable int projectId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(issueService.findIssueListByTypeByProjectId(projectId));
    }

    @PostMapping
    public ResponseEntity<Void> save(@AuthenticationPrincipal(errorOnInvalidType = true) SecurityUserDetailsDto user, @RequestBody @Valid IssueSaveRequest request){
        issueFacade.save(user.memberId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<IssueDetailsResponse> findIssueDetails(@PathVariable Integer issueId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(issueFacade.findIssueDetailsById(issueId));
    }

}
