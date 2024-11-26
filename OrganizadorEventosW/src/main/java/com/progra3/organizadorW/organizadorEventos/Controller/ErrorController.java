package com.progra3.organizadorW.organizadorEventos.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    @GetMapping
    public String error(){
        return "error-404";
    }
    
}
