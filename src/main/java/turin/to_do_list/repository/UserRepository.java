package turin.to_do_list.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import turin.to_do_list.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
