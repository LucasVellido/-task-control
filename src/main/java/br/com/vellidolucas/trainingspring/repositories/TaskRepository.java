package br.com.vellidolucas.trainingspring.repositories;

import br.com.vellidolucas.trainingspring.modelos.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t  WHERE t.user.email = :emailUser")
    List<Task> getTasksByUser(@Param("emailUser") String email);
}
