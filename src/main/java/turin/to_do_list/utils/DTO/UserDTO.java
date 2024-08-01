package turin.to_do_list.utils.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import turin.to_do_list.model.Role;

import java.time.LocalDate;

@Data
@Builder
public class UserDTO {
    private Integer id;
    private String username;
    private String email;
    private Role role;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private LocalDate createdAt;
}
