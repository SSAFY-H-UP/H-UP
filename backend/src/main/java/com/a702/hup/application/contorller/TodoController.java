package com.a702.hup.application.contorller;

import com.a702.hup.application.data.request.TodoAssigneeSaveRequest;
import com.a702.hup.application.data.request.TodoSaveRequest;
import com.a702.hup.application.data.request.TodoStatusUpdateRequest;
import com.a702.hup.application.facade.TodoFacade;
import com.a702.hup.domain.todo.TodoService;
import com.a702.hup.global.config.security.SecurityUserDetailsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {
    private final TodoService todoService;
    private final TodoFacade todoFacade;

    @PostMapping
    public ResponseEntity<Void> save(
            @AuthenticationPrincipal(errorOnInvalidType = true) SecurityUserDetailsDto user,
            @RequestBody TodoSaveRequest request) {
        todoFacade.save(user.memberId(), request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping
    public ResponseEntity<Void> save(
            @AuthenticationPrincipal(errorOnInvalidType = true) SecurityUserDetailsDto user,
            @RequestBody TodoAssigneeSaveRequest request) {
        todoFacade.saveAssignee(user.memberId(), request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("/status")
    public ResponseEntity<Void> updateStatus(
            @AuthenticationPrincipal(errorOnInvalidType = true) SecurityUserDetailsDto user,
            @RequestBody TodoStatusUpdateRequest request) {
        todoFacade.updateStatus(user.memberId(), request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
