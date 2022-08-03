package com.dh.clinica.service.impl;

import com.dh.clinica.excepciones.ResourceNotFoundException;
import com.dh.clinica.persistence.dto.OdontologoDTO;

import java.util.Set;

public interface IOdontologoService {

    OdontologoDTO crearOdontologo(OdontologoDTO odontologoDTO);
    OdontologoDTO buscarPorId(Integer id) throws ResourceNotFoundException;
    OdontologoDTO modificarOdontologo(OdontologoDTO odontologoDTO) throws ResourceNotFoundException;
    String eliminarOdontologo(Integer id) throws ResourceNotFoundException;
    Set<OdontologoDTO> buscarTodos();
}
