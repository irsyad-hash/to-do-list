package turin.to_do_list.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import turin.to_do_list.exception.InvalidCredentialsExeption;
import turin.to_do_list.exception.InvalidTokenException;
import turin.to_do_list.exception.UserAlreadyExistsException;
import turin.to_do_list.model.Role;
import turin.to_do_list.model.User;
import turin.to_do_list.repository.UserRepository;
import turin.to_do_list.security.JwtUtil;
import turin.to_do_list.service.AuthService;
import turin.to_do_list.utils.DTO.AuthDto;
import turin.to_do_list.utils.DTO.AuthResponse;
import turin.to_do_list.utils.DTO.responseDTO.RegisterResponse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private static final int MIN_PASSWORD_LENGTH = 8;

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AuthResponse login(AuthDto.LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(userDetails);
            String refreshToken = jwtUtil.generateRefreshToken(userDetails);
            return new AuthResponse(accessToken, refreshToken);
        } catch (AuthenticationException e) {
            throw new InvalidCredentialsExeption("Invalid username or password");
        }
    }

    @Override
    public RegisterResponse register(AuthDto.RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new UserAlreadyExistsException("Username is already taken");
        }

        List<String> passwordErrors = validatePassword(registerRequest.getPassword());
        if (!passwordErrors.isEmpty()) {
            throw new IllegalArgumentException("Password does not meet strength requirements: " + String.join(", ", passwordErrors));
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setRole(Role.USER);
        user.setCreateAt(LocalDate.now());
        userRepository.save(user);

        return RegisterResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    @Override
    public AuthResponse registerAdmin(AuthDto.RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new UserAlreadyExistsException("Username is already taken");
        }

        List<String> passwordErrors = validatePassword(registerRequest.getPassword());
        if (!passwordErrors.isEmpty()) {
            throw new IllegalArgumentException("Password does not meet strength requirements: " + String.join(", ", passwordErrors));
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setRole(Role.ADMIN);
        userRepository.save(user);

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(Role.ADMIN.name())
                .build();

        String accessToken = jwtUtil.generateAccessToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);
        return new AuthResponse(accessToken, refreshToken);
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        if (jwtUtil.validateRefreshToken(refreshToken)) {
            String username = jwtUtil.extractUsername(refreshToken);
            UserDetails userDetails = userRepository.findByUsername(username)
                    .map(user -> org.springframework.security.core.userdetails.User
                            .withUsername(user.getUsername())
                            .password(user.getPassword())
                            .authorities(user.getRole().name())
                            .build())
                    .orElseThrow(() -> new InvalidTokenException("User not found for the given token"));

            String newAccessToken = jwtUtil.generateAccessToken(userDetails);
            return new AuthResponse(newAccessToken, refreshToken);
        }
        throw new InvalidTokenException("Invalid refresh token");
    }

    private List<String> validatePassword(String password) {
        List<String> errors = new ArrayList<>();

        if (password.length() < MIN_PASSWORD_LENGTH) {
            errors.add("Password must be at least " + MIN_PASSWORD_LENGTH + " characters long");
        }
        if (!password.matches(".*[A-Z].*")) {
            errors.add("Password must contain at least one uppercase letter");
        }
        if (!password.matches(".*[a-z].*")) {
            errors.add("Password must contain at least one lowercase letter");
        }
        if (!password.matches(".*\\d.*")) {
            errors.add("Password must contain at least one digit");
        }
        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            errors.add("Password must contain at least one special character");
        }

        return errors;
    }
}
