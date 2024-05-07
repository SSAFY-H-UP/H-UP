package com.a702.hup.application.data.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class TodoAssigneeSaveRequest {
    @NotNull
    private int todoId;

    @NotNull
    private List<Integer> memberIdList;
}
