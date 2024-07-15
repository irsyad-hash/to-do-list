package turin.to_do_list.service;

import turin.to_do_list.model.TaskDetail;
import turin.to_do_list.utils.TaskDetailDTO;

import java.util.List;

public interface TaskDetailService {
    TaskDetail create(TaskDetailDTO newTask);
    TaskDetail getById(Integer id);
    List<TaskDetail> getAll();
    TaskDetail update(Integer id, TaskDetailDTO newTask);
    void delete(Integer id);

}
