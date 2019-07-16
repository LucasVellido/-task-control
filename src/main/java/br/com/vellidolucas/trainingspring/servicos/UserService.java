package br.com.vellidolucas.trainingspring.servicos;

import br.com.vellidolucas.trainingspring.modelos.User;
import br.com.vellidolucas.trainingspring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User encontrarPorEmail(String email){
        return userRepository.findByEmail(email);
    }

    public void salvar(User usuario){

        //Criptografando a senha do usuário de forma unidirecional.
        usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
        userRepository.save(usuario);
    }
}
