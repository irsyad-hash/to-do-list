package turin.to_do_list.service.impl;

import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import turin.to_do_list.model.Role;
import turin.to_do_list.model.Todo;
import turin.to_do_list.model.User;
import turin.to_do_list.repository.TodoRepository;
import turin.to_do_list.repository.UserRepository;
import turin.to_do_list.service.UserService;
import turin.to_do_list.utils.DTO.UserDTO;
import turin.to_do_list.utils.DTO.responseDTO.SingleUserResponse;
import turin.to_do_list.utils.DTO.specification.TodoSpecification;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserImpl implements UserService {
    private final  UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TodoRepository todoRepository;


    @Override
    public Page<User> getAll(Pageable pageble) {
        return userRepository.findAll(pageble);
    }

    @Override
    public SingleUserResponse getOne(Integer id) {
        User user= userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return SingleUserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(LocalDate.now())
                .build();
    }

    @Override
    public User updateRoleAdmin(Integer id, UserDTO userDTO) {
        User user = userRepository.findById(Math.toIntExact(id)).orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(userDTO.getRole());
        return userRepository.save(user);
    }

    @Override
    public User createSuperAdmin(UserDTO request) {
        return userRepository.save(User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .build());
    }

    @Override
    public Page<Todo> getAllAdmin(Pageable pageable, String status, String order, String sortBy, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("User not found"));
        Specification<Todo> specification = TodoSpecification.getSpecification(status, sortBy,order, user.getId());
        return todoRepository.findAll(specification,pageable);
    }

    @Override
    public Todo getOneAdmin(Integer id,Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(()-> new RuntimeException("User not found"));
        return todoRepository.findByUserAndId(user,id).orElseThrow(()-> new RuntimeException("Id not found")) ;
    }
}
