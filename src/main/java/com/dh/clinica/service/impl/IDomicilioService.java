package com.dh.clinica.service.impl;

import com.dh.clinica.excepciones.ResourceNotFoundException;
import com.dh.clinica.persistence.dto.DomicilioDTO;

import java.util.Set;

public interface IDomicilioService {

    DomicilioDTO crearDomicilio(DomicilioDTO domicilioDTO);
    DomicilioDTO buscarPorId(Integer id) throws ResourceNotFoundException;
    DomicilioDTO modificarDomicilio(DomicilioDTO domicilioDTO) throws ResourceNotFoundException;
    String eliminarDomicilio(Integer id) throws ResourceNotFoundException;
    Set<DomicilioDTO> buscarTodos();
}
