package lab.blps.security.bd.entities.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(
    name = "users",
    uniqueConstraints = {@UniqueConstraint(columnNames = "login")}
)
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotBlank
    @Column(name = "login")
    private String login;
    @NotBlank
    @Size(max = 120)
    @Column(name = "password")
    private String password;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "description")
    private String description;
    @Column(name = "amount_request", nullable = false)
    @Min(value = 0)
    private Integer amountRequest;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public User() {}

    public User(String login, String password, Integer amountRequest) {
        this.login = login;
        this.password = password;
        this.amountRequest = amountRequest;
    }

}