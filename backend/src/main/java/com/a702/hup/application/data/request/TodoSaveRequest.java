package com.a702.hup.application.data.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class TodoSaveRequest {
    private List<Integer> memberIdList;
    private Integer issueId;
    private String content;
}
