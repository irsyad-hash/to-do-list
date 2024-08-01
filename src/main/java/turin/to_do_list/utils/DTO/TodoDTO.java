package turin.to_do_list.utils.DTO;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import turin.to_do_list.model.Status;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
public class TodoDTO {
    private Integer id;
    private String title;
    private String description;

    @CreationTimestamp
    private Date dueDate;

    private String status;
    private LocalDate createAt;
}
