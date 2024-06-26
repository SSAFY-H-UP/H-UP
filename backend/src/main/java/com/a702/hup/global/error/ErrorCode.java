package com.a702.hup.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Global
    API_ERROR_INTERNAL_SERVER_ERROR(500, "G001", "서버 오류"),
    API_ERROR_INPUT_INVALID_VALUE(400, "G002", "잘못된 입력"),

    // Member
    API_ERROR_MEMBER_NOT_FOUND(400,"M001","멤버를 찾을 수 없음"),
    API_ERROR_IS_DELETED_MEMBER(400, "M002", "탈퇴된 계정"),

    // TeamMember
    API_ERROR_IS_NOT_TEAM_MEMBER(400, "TM001", "팀 소속 멤버가 아님"),

    // Team
    API_ERROR_TEAM_NOT_FOUND(400, "T001", "팀을 찾을 수 없음"),

    // Project
    API_ERROR_PROJECT_NOT_FOUND(400, "P001", "프로젝트를 찾을 수 없음"),
    API_ERROR_PROJECT_NOT_ROLE(400,"P002","해당 프로젝트에 대한 권한 없음"),

    // Project Member
    API_ERROR_PROJECT_MEMBER_NOT_FOUND(400, "PM001", "해당 프로젝트 소속 멤버 아님"),

    // Issue
    API_ERROR_ISSUE_NOT_FOUND(400,"I001","이슈를 찾을 수 없음"),
    API_ERROR_ISSUE_NOT_ROLE(400,"I002","해당 이슈에 대한 권한 없음"),

    // Document
    API_ERROR_DOCUMENT_NOT_FOUND(400, "D001", "문서를 찾을 수 없음"),

    // Document Member
    API_ERROR_ACTIVE_DOCUMENT_MEMBER_NOT_FOUND(400, "DM001", "사용자가 사용중인 문서를 찾을 수 없음"),

    // Agenda
    API_ERROR_AGENDA_NOT_FOUND(400,"A001","의사결정을 찾을 수 없음"),
    API_ERROR_AGENDA_MEMBER_NOT_FOUND(400,"A002","의사결정 담당자가 아닙니다"),

    // Todo
    API_ERROR_TODO_NOT_FOUND(400,"TD001","할 일을 찾을 수 없음"),
    API_ERROR_TODO_MEMBER_NOT_FOUND(400,"TD002","할 일 담당자가 아닙니다"),

    // Auth
    API_ERROR_USERNAME_NOT_FOUND(400, "AUTH001", "아이디 입력 오류"),
    API_ERROR_AUTHENTICATION_FAIL(401, "AUTH002", "인증 실패"),
    API_ERROR_TOKEN_NOT_FOUND(400, "AUTH003", "토큰이 없음"),
    API_ERROR_ACCESS_TOKEN_EXPIRED(401, "AUTH004", "만료된 토큰"),
    API_ERROR_REFRESH_TOKEN_EXPIRED(401, "AUTH005", "만료된 토큰"),
    API_ERROR_IS_MALFORMED_TOKEN(400, "AUTH006", "잘못된 형식의 토큰"),
    API_ERROR_UNAUTHORIZED(403, "AUTH007", "권한 없음"),

    ;
    private final int status;
    private final String code;
    private final String message;
}
