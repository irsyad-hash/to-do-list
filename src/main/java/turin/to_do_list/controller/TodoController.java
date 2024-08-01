package turin.to_do_list.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import turin.to_do_list.model.Todo;
import turin.to_do_list.service.TodoService;
import turin.to_do_list.utils.DTO.TodoDTO;
import turin.to_do_list.utils.DTO.responseDTO.Response;
import turin.to_do_list.utils.DTO.responseDTO.TodoPageResponse;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TodoDTO request, Authentication authentication){
        return ResponseEntity.status(201).body(todoService.create(request,authentication));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id, Authentication authentication){
        return ResponseEntity.ok(todoService.getOne(id,authentication));
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @PageableDefault(size = 10) Pageable pageable,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String order,
            Authentication authentication
    ) {
        return Response.renderJSON(
                new TodoPageResponse<>(todoService.getAll(pageable, status, sortBy, order, authentication))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTodoItem(@PathVariable Integer id, @RequestBody TodoDTO request,Authentication authentication){
        return ResponseEntity.ok(todoService.updateTodoItem(id,request,authentication));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Integer id, @RequestBody TodoDTO updatedTask, Authentication authentication){
        return ResponseEntity.ok(todoService.updateStatus(id,updatedTask,authentication));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id, Authentication authentication) {
        todoService.deleteById(id,authentication);
        return ResponseEntity.noContent().build();
    }


}
