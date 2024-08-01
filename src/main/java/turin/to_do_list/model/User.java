package turin.to_do_list.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false, unique = true)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "createAt")
    private LocalDate createAt;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Todo> todos;

}
