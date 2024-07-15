package turin.to_do_list.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Tasks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String task_name;
    private String description;
}
