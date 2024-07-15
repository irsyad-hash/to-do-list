package turin.to_do_list.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import turin.to_do_list.model.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
