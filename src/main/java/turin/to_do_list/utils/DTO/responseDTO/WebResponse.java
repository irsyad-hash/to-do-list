package turin.to_do_list.utils.DTO.responseDTO;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebResponse<T> {
    private String message;
    private String status;
    private T data;
}
