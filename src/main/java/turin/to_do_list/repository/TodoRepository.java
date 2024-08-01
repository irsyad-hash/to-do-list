package turin.to_do_list.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import turin.to_do_list.model.Todo;
import turin.to_do_list.model.User;

import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Integer>, JpaSpecificationExecutor<Todo> {
    Optional<Todo> findByUserAndId(User user, Integer id);
}
