package com.a702.hup.domain.todo;

import com.a702.hup.domain.todo.entity.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    public void save(Todo todo) {
        todoRepository.save(todo);
    }
}
