package com.a702.hup.application.facade;

import com.a702.hup.application.data.request.TodoSaveRequest;
import com.a702.hup.domain.issue.IssueService;
import com.a702.hup.domain.member.MemberService;
import com.a702.hup.domain.todo.TodoService;
import com.a702.hup.domain.todo.entity.Todo;
import com.a702.hup.domain.todo.entity.TodoStatus;
import com.a702.hup.domain.todo_member.entity.TodoMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoFacade {
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
        Todo todo = Todo.builder()
                .issue(issueService.findById(request.getIssueId()))
                .requester(memberService.findById(requesterId))
                .status(TodoStatus.ASSIGNED)
                .content(request.getContent())
                .build();
        addTodoMember(todo, request.getMemberIdList());
        todoService.save(todo);
    }

    /**
     * @author 손현조
     * @date 2024-05-07
     * @description 할 일 담당자 추가
     **/
    private void addTodoMember(Todo todo, List<Integer> memberIdList) {
        memberIdList.stream()
                .map(memberId -> TodoMember.builder()
                        .member(memberService.findById(memberId))
                        .todo(todo)
                        .build())
                .forEach(todoMember -> todo.getTodoMemberList().add(todoMember));
    }
}
