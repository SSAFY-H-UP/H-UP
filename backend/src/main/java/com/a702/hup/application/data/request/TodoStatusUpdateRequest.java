package com.a702.hup.application.data.request;

import com.a702.hup.domain.todo.entity.TodoStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoStatusUpdateRequest {
    private Integer todoId;
    private TodoStatus todoStatus;
}
