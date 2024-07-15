package turin.to_do_list.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import turin.to_do_list.model.User;
import turin.to_do_list.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public User Create(@RequestBody User request){
        return userService.create(request);
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Integer id) {
        return userService.getById(id);
    }

    @GetMapping
    public List<User> getAll(){
        return userService.getAll();
    }

    @PutMapping("{id}")
    public User update(@PathVariable Integer id, @RequestBody User user) {
        return userService.update(id, user);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Integer id){
        userService.deleteById(id);}
}
