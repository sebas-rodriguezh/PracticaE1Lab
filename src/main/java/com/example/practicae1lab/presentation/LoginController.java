package com.example.practicae1lab.presentation;

import com.example.practicae1lab.logic.Service;
import com.example.practicae1lab.logic.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController
{

    @Autowired
    private Service service;

    @GetMapping("/")
    public String index (HttpSession session, Model model)
    {
        if (session.getAttribute("usuarioId") != null)
        {
            return "redirect:/documentos"; //Si se logra iniciar sesión.
        }
        return "presentation/index";
    }

    @GetMapping("/VolverAInicio")
    public String indexVolver (HttpSession session, Model model)
    {
        if (session.getAttribute("usuarioId") != null)
        {
            return "presentation/index";
        }
        return "presentation/index";
    }


    @GetMapping("/login")
    public String mostrarLogin(HttpSession session)
    {
        if (session.getAttribute("usuarioId") != null)
        {
            return "redirect:/documentos"; //Si se logra iniciar sesión.
        }
        return "presentation/index";
    }


    @PostMapping("/login")
    public String procesarLogin (HttpSession session, Model model, @RequestParam String usuarioId, @RequestParam String password)
    {
        if (usuarioId == null || usuarioId.trim().isEmpty() || password == null || password.trim().isEmpty())
        {
            model.addAttribute("error", "Usuario y contraseña son requeridos");
            return "presentation/index";
        }
        Usuario usuario = service.autenticar(usuarioId, password);

        if (usuario != null) {
            session.setAttribute("usuarioId", usuario.getId());
            return "redirect:/documentos"; //Si se logra iniciar sesión.
        } else
        {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "presentation/index";

        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session)
    {
        session.invalidate();
        return "redirect:/";
    }

}
