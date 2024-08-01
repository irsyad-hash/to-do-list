package turin.to_do_list.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import turin.to_do_list.model.User;
import turin.to_do_list.service.UserService;
import turin.to_do_list.utils.DTO.UserDTO;
import turin.to_do_list.utils.DTO.responseDTO.Response;
import turin.to_do_list.utils.DTO.responseDTO.TodoPageResponse;
import turin.to_do_list.utils.DTO.responseDTO.UserPageResponse;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Value("${superadmin.secret}")
    private String superAdminSecretKey;
    @Value("${admin.secret}")
    private String adminSecretKey;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> getAll(
            @PageableDefault(size = 10)Pageable pageable
    ){
        return Response.renderJSON(
                new UserPageResponse<>(userService.getAll(pageable))
        );
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_USER')")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getOne(id));
    }

    @PatchMapping("/users/{id}/role")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_USER')")
    public ResponseEntity<?> updateRole(@PathVariable Integer id, @Valid @RequestBody UserDTO req, @RequestHeader(value = "X-Admin-Secret-Key") String key) {
        if (key == null || !key.equals(adminSecretKey)) {
            return Response.renderJSON(
                    null,
                    "Invalid Admin Secret Key",
                    HttpStatus.UNAUTHORIZED
            );
        }
        return Response.renderJSON(
                userService.updateRoleAdmin(id, req)
        );
    }

    @PostMapping("/super-admin")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> createSuperAdmin(@Valid @RequestBody UserDTO req, @RequestHeader(value = "X-Super-Admin-Secret-Key") String key) {
        if (key == null || !key.equals(superAdminSecretKey)) {
            return Response.renderJSON(
                    null,
                    "Invalid Super Admin Secret Key",
                    HttpStatus.UNAUTHORIZED
            );
        }
        return Response.renderJSON(
                userService.createSuperAdmin(req),
                "User created successfully",
                HttpStatus.CREATED
        );
    }
    @GetMapping("/admin/todos")
    public ResponseEntity<?> getAll(
            @PageableDefault(size = 10) Pageable pageable,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String order,
            Authentication authentication
    ) {
        return Response.renderJSON(
                new TodoPageResponse<>(userService.getAllAdmin(pageable, status, sortBy, order, authentication))
        );
    }

    @GetMapping("/admin/todos/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id, Authentication authentication){
        return ResponseEntity.ok(userService.getOneAdmin(id,authentication));
    }

}
