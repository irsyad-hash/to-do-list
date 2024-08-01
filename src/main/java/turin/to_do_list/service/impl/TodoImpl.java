package turin.to_do_list.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import turin.to_do_list.model.Status;
import turin.to_do_list.model.Todo;
import turin.to_do_list.model.User;
import turin.to_do_list.repository.TodoRepository;
import turin.to_do_list.repository.UserRepository;
import turin.to_do_list.service.TodoService;
import turin.to_do_list.service.UserService;
import turin.to_do_list.utils.DTO.TodoDTO;
import turin.to_do_list.utils.DTO.responseDTO.TodoPageResponse;
import turin.to_do_list.utils.DTO.specification.TodoSpecification;



import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TodoImpl implements TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public Todo create(TodoDTO newTask, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("User not found"));
        return todoRepository.save(Todo.builder()
                .user(user)
                .title(newTask.getTitle())
                .description(newTask.getDescription())
                .dueDate(newTask.getDueDate())
                .createAt(LocalDate.now())
                .status(Status.PENDING)
                .build()
        );
    }
    @Override
    public Todo getOne(Integer id,Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(()-> new RuntimeException("User not found"));
        return todoRepository.findByUserAndId(user,id).orElseThrow(()-> new RuntimeException("Id not found")) ;
    }

    @Override
    public Page<Todo> getAll(Pageable pageable, String status, String order, String sortBy, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("User not found"));
        Specification<Todo> specification = TodoSpecification.getSpecification(status, sortBy,order, user.getId());
        return todoRepository.findAll(specification,pageable);
    }

    @Override
    public Todo updateTodoItem(Integer id, TodoDTO updatedTask, Authentication authentication) {
        Todo todo = getOne(id, authentication);
        todo.setTitle(updatedTask.getTitle());
        todo.setDescription(updatedTask.getDescription());
        todo.setDueDate(updatedTask.getDueDate());

        return todoRepository.save(todo);
    }

    @Override
    public Todo updateStatus(Integer id, TodoDTO updatedTask, Authentication authentication) {
        Todo todo = getOne(id, authentication);
        todo.setStatus(Status.valueOf(updatedTask.getStatus()));
        return todoRepository.save(todo);
    }

    @Override
    public void deleteById(Integer id, Authentication authentication) {
        Todo todo = getOne(id, authentication);
        todoRepository.delete(todo);
    }

}
