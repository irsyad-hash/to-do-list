package turin.to_do_list.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import turin.to_do_list.model.Task;
import turin.to_do_list.repository.TaskRepository;
import turin.to_do_list.service.TaskService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Override
    public Task create(Task newTask) {
        return taskRepository.save(Task.builder()
                .task_name(newTask.getTask_name())
                .description(newTask.getDescription())
                .build()
        );
    }

    @Override
    public Task getById(Integer id) {
        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task_id not found"));
    }

    @Override
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task update(Integer id, Task updatedTask) {
        Task task = getById(id);
        task.setTask_name(updatedTask.getTask_name());
        task.setDescription(updatedTask.getDescription());
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        taskRepository.deleteById(id);
    }
}
