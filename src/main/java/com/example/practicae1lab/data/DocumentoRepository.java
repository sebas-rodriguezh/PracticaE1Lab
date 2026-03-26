package com.example.practicae1lab.data;

import com.example.practicae1lab.logic.Documento;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentoRepository extends CrudRepository<Documento, String> {
    List<Documento> findByTipoId(String tipoId);
}
