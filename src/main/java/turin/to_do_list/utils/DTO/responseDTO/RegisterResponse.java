package turin.to_do_list.utils.DTO.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterResponse {
    private String username;
    private String email;
    private String password;

}
