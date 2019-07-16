package br.com.vellidolucas.trainingspring.modelos;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    @NotNull(message = "O título é obrigatório")
    @Length(max = 50, min = 3, message = "O título deve conter entre 3 e 50 caracteres")
    private String title;

    @Column(length = 100, nullable = true)
    @Length(max = 100, message = "A descrição deve conter até 100 caracteres")
    private String description;

    @Column(name = "tar_data_expiracao", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expirationDate;

    @Column(name = "tar_concluida", nullable = false)
    private Boolean completed = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = " usr_id")
    private User user;

    public Task() {
    }

    public Task(Long id, String title, String description, Date expirationDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.expirationDate = expirationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
