package com.progra3.organizadorW.organizadorEventos.Controller;

import com.progra3.organizadorW.organizadorEventos.Service.CookieService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Objects;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final CookieService cookieService;

    public UsuarioController(CookieService cookieService) {
        this.cookieService = cookieService;
    }

    @GetMapping("/home")
    public String inicio(HttpServletRequest res, HttpServletResponse response, Model model) {
        String cookie = cookieService.getCookie(res,"login");
        if(!Objects.isNull(cookie)){
            String[] ids = cookie.split("-");
            cookieService.deleteCookie(response,"login");
            return "redirect:/invitaciones/evento?id="+ids[0]+"&usuario="+ids[1];
        }
        return "index";
    }

}
