package br.com.vellidolucas.trainingspring.repositories;

import br.com.vellidolucas.trainingspring.modelos.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
