package turin.to_do_list.utils;

import lombok.Data;
import turin.to_do_list.model.Task;
import turin.to_do_list.model.User;

@Data
public class TaskDetailDTO {
    private String name;
    private String username;
    private String password;
    private Task task;
    private User user;
}
