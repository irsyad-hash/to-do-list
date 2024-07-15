package turin.to_do_list.service;

import turin.to_do_list.model.User;

import java.util.List;

public interface UserService {
    User create(User newUser);
    User getById(Integer id);
    List<User> getAll();
    User update(Integer id, User updatedUser);
    void  deleteById (Integer id);
}
