package turin.to_do_list.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import turin.to_do_list.model.Task;
import turin.to_do_list.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PutMapping
    public Task create(@RequestBody Task request){
        return taskService.create(request);
    }

    @GetMapping("/{id}")
    public Task getById(@PathVariable Integer id){
        return taskService.getById(id);
    }

    @GetMapping
    public List<Task> getAll() {
        return taskService.getAll();
    }

    @PutMapping("{id}")
    public Task update(@PathVariable Integer id, @RequestBody Task request){
        return taskService.update(id,request);
    }

}
