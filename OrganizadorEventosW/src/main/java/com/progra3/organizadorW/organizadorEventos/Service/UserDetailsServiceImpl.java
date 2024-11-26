package com.progra3.organizadorW.organizadorEventos.Service;

import com.progra3.organizadorW.organizadorEventos.Models.SecurityUsuario;
import com.progra3.organizadorW.organizadorEventos.Models.UsuarioModel;
import com.progra3.organizadorW.organizadorEventos.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscar el cliente por correo (username)
        UsuarioModel user = userRepository.findByNombre(username);

        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        // Validación adicional de los campos de correo y contraseña
        if (user.getNombre() == null || user.getPass() == null) {
            throw new IllegalArgumentException("Correo o contraseña no pueden ser nulos o vacíos");
        }

        // Devolver el objeto UserDetails
        return User.withUsername(user.getNombre())
                .password(user.getPass())
                .authorities("USER")
                .build();
    }
}
