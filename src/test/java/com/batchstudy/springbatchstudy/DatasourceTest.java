package com.batchstudy.springbatchstudy;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.batchstudy.springbatchstudy.system1.User;
import com.batchstudy.springbatchstudy.system1.UserRepository;
import com.batchstudy.springbatchstudy.system2.Usuario;
import com.batchstudy.springbatchstudy.system2.UsuarioRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DatasourceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Test
    public void testarInsertUser() {

        User user = new User();
        user.setId(1L);
        user.setNomeCompleto("Nome Completo");

        User userSaved = userRepository.save(user);
        Optional<User> userDB = userRepository.findById(userSaved.getId());

        assertTrue(userDB.isPresent());

    }

    @Test
    public void testarInsertUsuario() {

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Nome");

        Usuario userSaved = usuarioRepository.save(usuario);
        Optional<Usuario> userDB = usuarioRepository.findById(userSaved.getId());

        assertTrue(userDB.isPresent());

    }
}
