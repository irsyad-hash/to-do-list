package turin.to_do_list.service;

import turin.to_do_list.model.Task;

import java.util.List;

public interface TaskService {
    Task create(Task newTask);
    Task getById(Integer id);
    List<Task> getAll();
    Task update(Integer id, Task updatedTask);
    void deleteById (Integer id);
}
