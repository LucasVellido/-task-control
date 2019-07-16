package br.com.vellidolucas.trainingspring.modelos;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @NotNull(message = "E-mail obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    @Column(nullable = false, length = 100)
    @NotNull(message = "Senha obrigatória")
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY) //One (um usuario) To Many (varias tarefas) OneToMany One = Usuario (classe) / Many = Tarefas (atributo).
    private List<Task> tasks;

    public User() {
    }

    public User(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
