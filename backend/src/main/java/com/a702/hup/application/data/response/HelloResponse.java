package com.a702.hup.application.data.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class HelloResponse {
    private String body;

    public HelloResponse(String body) {
        this.body = body;
    }
}
