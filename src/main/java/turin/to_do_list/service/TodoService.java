package turin.to_do_list.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import turin.to_do_list.model.Todo;
import turin.to_do_list.utils.DTO.TodoDTO;

import java.util.List;

public interface TodoService {
    Todo create(TodoDTO newTask, Authentication authentication);
    Todo getOne(Integer id, Authentication authentication);
    Page<Todo> getAll(Pageable page, String status, String order, String sortBy, Authentication authentication);
    Todo updateTodoItem(Integer id, TodoDTO updatedTask, Authentication authentication);
    Todo updateStatus(Integer id, TodoDTO updatedTask, Authentication authentication);
    void deleteById (Integer id, Authentication authentication);
}
