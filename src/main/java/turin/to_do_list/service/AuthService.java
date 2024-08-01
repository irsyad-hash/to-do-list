package turin.to_do_list.service;

import turin.to_do_list.utils.DTO.AuthDto;
import turin.to_do_list.utils.DTO.AuthResponse;
import turin.to_do_list.utils.DTO.responseDTO.RegisterResponse;

public interface AuthService {
    AuthResponse login(AuthDto.LoginRequest loginRequest);
    RegisterResponse register(AuthDto.RegisterRequest registerRequest);
    AuthResponse registerAdmin(AuthDto.RegisterRequest registerRequest);
    AuthResponse refreshToken(String refreshToken);
}
