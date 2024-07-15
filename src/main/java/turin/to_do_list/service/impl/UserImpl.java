package turin.to_do_list.service.impl;

import lombok.*;
import org.springframework.stereotype.Service;
import turin.to_do_list.model.User;
import turin.to_do_list.repository.UserRepository;
import turin.to_do_list.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserImpl implements UserService {
    private final  UserRepository userRepository;

    @Override
    public User create(User newUser) {
        return userRepository.save(User.builder()
                .name(newUser.getName())
                .email(newUser.getEmail())
                .password(newUser.getPassword())
                .build());
    }

    @Override
    public User getById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User update(Integer id, User updatedUser) {
        User newUser = getById(id);
        newUser.setEmail(updatedUser.getEmail());
        newUser.setName(updatedUser.getName());
        newUser.setPassword(updatedUser.getPassword());
        return userRepository.save(newUser);
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }
}
