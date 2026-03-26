package com.example.practicae1lab.presentation;


import com.example.practicae1lab.logic.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
public class DocumentoController {

    @Autowired
    private Service service;

    @GetMapping("/documentos")
    public String mostrarDocumentos(HttpSession session, Model model)
    {
        String usuarioId = (String) session.getAttribute("usuarioId");

        if (usuarioId == null) {
            return "redirect:/";
        }

        List<Tipo> tipos = service.obtenerTipos();
        List <Linea> carrito = service.obtenerCarrito(usuarioId);
        Double total = service.calcularTotalCarrito(usuarioId);
        List <Documento> documentos = service.obtenerDocumentos();

        //model.addAttribute("documentos", documentos);
        model.addAttribute("tipos", tipos);
        model.addAttribute("total", total);
        model.addAttribute("carrito", carrito);

        return "presentation/documentos/documentos";
    }

    @GetMapping("/filtrar")
    public String filtrarDocumentos (HttpSession session, Model model, @RequestParam String tipoId)
    {
        String usuarioId = (String) session.getAttribute("usuarioId");

        if (usuarioId == null) {
            return "redirect:/";
        }

        List<Tipo> tipos = service.obtenerTipos();
        List <Linea> carrito = service.obtenerCarrito(usuarioId);
        Double total = service.calcularTotalCarrito(usuarioId);
        List<Documento> documentos = service.obtenerDocumentosPorTipo(tipoId);

        model.addAttribute("tipos", tipos);
        model.addAttribute("total", total);
        model.addAttribute("carrito", carrito);
        model.addAttribute("documentos", documentos);
        model.addAttribute("tipoSeleccionado", tipoId);


        return "presentation/documentos/documentos";
    }

    @PostMapping("/agregar")
    public String agregarAlCarrito(
            HttpSession session, Model model, @RequestParam String documentoId)
    {
        String usuarioId = (String) session.getAttribute("usuarioId");

        if (usuarioId == null) {
            return "redirect:/";
        }

        service.agregarDocumentoAlCarrito(usuarioId, documentoId);
        return "redirect:/documentos";
    }
}
