package com.a702.hup.domain.todo;

import com.a702.hup.application.data.request.TodoStatusUpdateRequest;
import com.a702.hup.domain.todo.entity.Todo;
import com.a702.hup.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    /**
     * @author 손현조
     * @date 2024-05-07
     * @description 할 일 상태 변경 (할 일 담당자가 실행)
     **/
    public void updateStatus(TodoStatusUpdateRequest request) {
        Todo todo = findById(request.getTodoId());
        todo.updateTodoStatus(request.getTodoStatus());
        save(todo);
    }

    public void save(Todo todo) {
        todoRepository.save(todo);
    }

    public Todo findById(Integer id) {
        return todoRepository.findById(id).orElseThrow(
                () -> new TodoException(ErrorCode.API_ERROR_TODO_NOT_FOUND));
    }

}
