package turin.to_do_list.controller;
import org.springframework.http.HttpStatus;
import turin.to_do_list.model.User;
import turin.to_do_list.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import turin.to_do_list.utils.DTO.AuthDto;
import turin.to_do_list.utils.DTO.AuthResponse;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDto.LoginRequest loginRequest) {
        return ResponseEntity.status(201).body(authService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthDto.RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody String refreshToken) {
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }

}
