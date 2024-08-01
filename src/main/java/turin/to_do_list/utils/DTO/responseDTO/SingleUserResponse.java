package turin.to_do_list.utils.DTO.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import turin.to_do_list.model.Role;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SingleUserResponse {
    private Integer id;
    private String username;
    private String email;
    private Role role;
    private LocalDate createdAt;
}
