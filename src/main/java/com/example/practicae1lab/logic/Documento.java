package com.example.practicae1lab.logic;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "documento")
public class Documento {
    @Id
    @Size(max = 10)
    @Column(name = "id", nullable = false, length = 10)
    private String id;

    @Size(max = 50)
    @NotNull
    @Column(name = "descripcion", nullable = false, length = 50)
    private String descripcion;

    @Column(name = "monto")
    private Float monto;

    @Column(name = "timbres")
    private Float timbres;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo")
    private Tipo tipo;

    @OneToMany(mappedBy = "documento")
    private Set<Linea> lineas = new LinkedHashSet<>();


}