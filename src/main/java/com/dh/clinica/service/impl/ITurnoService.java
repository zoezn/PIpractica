package com.dh.clinica.service.impl;


import com.dh.clinica.excepciones.ResourceNotFoundException;
import com.dh.clinica.persistence.dto.TurnoDTO;
import com.dh.clinica.persistence.model.Turno;

import java.util.Set;

public interface ITurnoService {
    TurnoDTO crearTurno(TurnoDTO turnoDTO) throws ResourceNotFoundException;
    TurnoDTO buscarPorId(Integer id) throws ResourceNotFoundException;
    TurnoDTO modificarTurno(TurnoDTO turnoDTO) throws ResourceNotFoundException;
    String eliminarTurno(Integer id) throws ResourceNotFoundException;
    Set<TurnoDTO> buscarTodos();
}
