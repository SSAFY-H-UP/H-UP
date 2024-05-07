package com.a702.hup.application.facade;

import com.a702.hup.application.data.request.TodoAssigneeSaveRequest;
import com.a702.hup.application.data.request.TodoSaveRequest;
import com.a702.hup.application.data.request.TodoStatusUpdateRequest;
import com.a702.hup.domain.issue.IssueService;
import com.a702.hup.domain.issue.entity.Issue;
import com.a702.hup.domain.issue_member.IssueMemberService;
import com.a702.hup.domain.member.MemberService;
import com.a702.hup.domain.member.entity.Member;
import com.a702.hup.domain.todo.TodoException;
import com.a702.hup.domain.todo.TodoService;
import com.a702.hup.domain.todo.entity.Todo;
import com.a702.hup.domain.todo_member.entity.TodoMember;
import com.a702.hup.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoFacade {
    private final IssueMemberService issueMemberService;
    private final MemberService memberService;
    private final IssueService issueService;
    private final TodoService todoService;

    /**
     * @author 손현조
     * @date 2024-05-07
     * @description 할 일 생성 (할 일 생성자가 실행)
     **/
    @Transactional
    public void save(Integer requesterId, TodoSaveRequest request) {
        Member requester = memberService.findById(requesterId);
        Issue issue = issueService.findById(request.getIssueId());
        issueMemberService.validationRole(issue, requester);
        todoService.save(issue, requester, request.getContent());
    }

    /**
     * @author 손현조
     * @date 2024-05-07
     * @description 할 일 담당자 추가
     **/
    @Transactional
    public void saveAssignee(int memberId, TodoAssigneeSaveRequest request) {
        Todo todo = todoService.findById(request.getTodoId());
        validation(todo,memberId);
        addTodoMember(todo, request.getMemberIdList());
    }

    private void addTodoMember(Todo todo, List<Integer> memberIdList) {
        memberIdList.stream()
                .map(memberId -> TodoMember.builder()
                        .member(memberService.findById(memberId))
                        .todo(todo)
                        .build())
                .forEach(todoMember -> todo.getTodoMemberList().add(todoMember));
    }

    /**
     * @author 손현조
     * @date 2024-05-07
     * @description 할 일 상태 변경 (할 일 담당자가 실행)
     **/
    public void updateStatus(Integer memberId, TodoStatusUpdateRequest request) {
    }

    /**
     * @author 손현조
     * @date 2024-05-07
     * @description 해당 이슈에 권한이 있는지 검사
     */
    private void validation(Todo todo,int memberId){
        Member member = memberService.findById(memberId);
        issueMemberService.validationRole(todo.getIssue(), member);
    }
}
