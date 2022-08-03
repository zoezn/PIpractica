package com.dh.clinica.service.impl;

import com.dh.clinica.excepciones.ResourceNotFoundException;
import com.dh.clinica.persistence.dto.PacienteDTO;

import java.util.Set;

public interface IPacienteService {

    PacienteDTO crearPaciente(PacienteDTO pacienteDTO);
    PacienteDTO buscarPorId(Integer id) throws ResourceNotFoundException;
    PacienteDTO modificarPaciente(PacienteDTO pacienteDTO) throws ResourceNotFoundException;
    String eliminarPaciente(Integer id) throws ResourceNotFoundException;
    Set<PacienteDTO> buscarTodos();
}
