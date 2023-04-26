package com.cursojava.curso.controllers;

import com.cursojava.curso.dao.UsuarioDao;
import com.cursojava.curso.models.Usuario;
import com.cursojava.curso.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "api/usuarios", method = RequestMethod.GET)
    public List<Usuario> getUsuarios(@RequestHeader(value="Authorization")String token) {
        if(!validarToken(token))return null;
        return usuarioDao.getUsuarios();
    }

    private boolean validarToken(String token){
        String userId = jwtUtil.getKey(token);
        return userId!=null;
    }

    @RequestMapping(value = "api/usuarios", method = RequestMethod.POST)
    public void registrarUsuario(@RequestBody Usuario usuario) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1, 1024, 1, usuario.getPassword());
        usuario.setPassword(hash);
        usuarioDao.registrarUsuario(usuario);
    }

    @RequestMapping(value = "api/usuarios/{id}", method = RequestMethod.GET)
    public Usuario getUsuario(@RequestHeader(value="Authorization")String token,@PathVariable long id) {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Lucas");
        usuario.setApellido("Moy");
        usuario.setEmail("xxx@xxx.es");
        usuario.setTelefono("123123123");
        usuario.setPassword("3123123");

        return usuario;
    }

    @RequestMapping(value = "listUsuarios")
    public List<Usuario> getListUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Lucas");
        usuario.setApellido("Moy");
        usuario.setEmail("xxx@xxx.es");
        usuario.setTelefono("123123123");
        usuario.setPassword("3123123");

        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setNombre("Maria");
        usuario2.setApellido("Caceres");
        usuario2.setEmail("xxx2@xxx.es");
        usuario2.setTelefono("1231233123");
        usuario2.setPassword("3123123");

        Usuario usuario3 = new Usuario();
        usuario3.setId(3L);
        usuario3.setNombre("Josefa");
        usuario3.setApellido("Pia");
        usuario3.setEmail("xxx3@xxx.es");
        usuario3.setTelefono("123123423");
        usuario3.setPassword("3123123");

        usuarios.add(usuario);
        usuarios.add(usuario2);
        usuarios.add(usuario3);

        return usuarios;
    }

    @RequestMapping(value = "editar")
    public Usuario editar() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Lucas");
        usuario.setApellido("Moy");
        usuario.setEmail("xxx@xxx.es");
        usuario.setTelefono("123123123");
        usuario.setPassword("3123123");
        return usuario;
    }

    @RequestMapping(value = "api/usuarios/{id}", method = RequestMethod.DELETE)
    public void eliminar(@RequestHeader(value="Authorization")String token,@PathVariable Long id) {
        if(!validarToken(token))return ;
        usuarioDao.eliminar(id);
    }

    @RequestMapping(value = "buscar")
    public Usuario buscar() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Lucas");
        usuario.setApellido("Moy");
        usuario.setEmail("xxx@xxx.es");
        usuario.setTelefono("123123123");
        usuario.setPassword("3123123");
        return usuario;
    }
}
