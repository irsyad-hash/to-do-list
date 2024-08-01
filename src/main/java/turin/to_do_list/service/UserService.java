package turin.to_do_list.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import turin.to_do_list.model.Todo;
import turin.to_do_list.model.User;
import turin.to_do_list.utils.DTO.UserDTO;
import turin.to_do_list.utils.DTO.responseDTO.SingleUserResponse;

import java.util.List;

public interface UserService {
    Page<User> getAll(Pageable pageable);
    SingleUserResponse getOne(Integer id);
    User updateRoleAdmin(Integer id, UserDTO userDTO);
    User createSuperAdmin(UserDTO request);
    Page<Todo> getAllAdmin(Pageable pageable, String status, String sortBy, String order, Authentication authentication);
    Todo getOneAdmin(Integer id, Authentication authentication);
}
