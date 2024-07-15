package turin.to_do_list.service.impl;

import turin.to_do_list.model.TaskDetail;
import turin.to_do_list.service.TaskDetailService;
import turin.to_do_list.utils.TaskDetailDTO;

import java.util.List;

public class TaskDetailImpl implements TaskDetailService {
    @Override
    public TaskDetail create(TaskDetailDTO newTask) {
        return null;
    }

    @Override
    public TaskDetail getById(Integer id) {
        return null;
    }

    @Override
    public List<TaskDetail> getAll() {
        return List.of();
    }

    @Override
    public TaskDetail update(Integer id, TaskDetailDTO newTask) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}
