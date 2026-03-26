package com.example.practicae1lab.logic;

import com.example.practicae1lab.data.DocumentoRepository;
import com.example.practicae1lab.data.LineaRepository;
import com.example.practicae1lab.data.TipoRepository;
import com.example.practicae1lab.data.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

@org.springframework.stereotype.Service
public class Service
{
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TipoRepository tipoRepository;

    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    private LineaRepository lineaRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Usuario autenticar (String idUsuario, String password)
    {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if (usuario != null)
        {
            if (passwordEncoder.matches(password, usuario.getClave()))
            {
                return usuario;
            }
        }
        return null;
    }

    public List <Tipo> obtenerTipos()
    {
        List<Tipo> tipos = new ArrayList<>();
        tipoRepository.findAll().forEach(tipos::add);
        return tipos;
    }

    public List <Linea> obtenerCarrito(String usuarioId)
    {
        return lineaRepository.findByUsuarioId(usuarioId);
    }

    public Double calcularTotalCarrito(String usuarioId)
    {
        List<Linea> carrito = lineaRepository.findByUsuarioId(usuarioId);
        Double total = 0.0;

        for (Linea linea : carrito)
        {
            Documento documento = linea.getDocumento();
            double precioDocumento = (documento.getMonto() != null ? documento.getMonto() : 0.0) + (documento.getTimbres() != null ? documento.getTimbres() : 0.0);
            total += precioDocumento * linea.getCantidad();
        }
        return total;
    }

    public List <Documento> obtenerDocumentos () {
        List<Documento> documentos = new ArrayList<>();
        documentoRepository.findAll().forEach(documentos::add);
        return documentos;
    }

    public List<Documento> obtenerDocumentosPorTipo(String tipoId) {
        return documentoRepository.findByTipoId(tipoId);
    }

    public void agregarDocumentoAlCarrito(String usuarioId, String documentoId)
    {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        Documento documento = documentoRepository.findById(documentoId).orElse(null);

        if (usuario == null || documento == null)
            throw new RuntimeException("Usuario o documento no encontrado");

        Optional<Linea> lineaExistente = lineaRepository.findByUsuarioIdAndDocumentoId(usuarioId, documentoId);
        Linea linea;

        if (lineaExistente.isPresent())
        {
            linea = lineaExistente.get();
            linea.setCantidad(linea.getCantidad() + 1);
        }
        else
        {
            linea = new Linea();
            linea.setDocumento(documento);
            linea.setUsuario(usuario);
            linea.setCantidad(1);
        }
        lineaRepository.save(linea);
    }
}
