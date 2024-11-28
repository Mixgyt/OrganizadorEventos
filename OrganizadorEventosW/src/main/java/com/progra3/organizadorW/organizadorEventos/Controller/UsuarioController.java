package com.progra3.organizadorW.organizadorEventos.Controller;

import com.progra3.organizadorW.organizadorEventos.Service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @GetMapping("/home")
    public String inicio(Model model){
        return "index";
    }

}
