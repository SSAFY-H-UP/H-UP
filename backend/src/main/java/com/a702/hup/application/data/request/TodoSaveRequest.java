package com.a702.hup.application.data.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoSaveRequest {
    private Integer issueId;
    private String content;
}
