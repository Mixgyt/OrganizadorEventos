package com.progra3.organizadorW.organizadorEventos.Service;

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

    private final UsuarioRepository userRepository;

    public UserDetailsServiceImpl(UsuarioRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioModel user = userRepository.findByNombre(username);

        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        return User.withUsername(user.getNombre())
                .password(user.getPass())
                .authorities("USER")
                .build();
    }
}
