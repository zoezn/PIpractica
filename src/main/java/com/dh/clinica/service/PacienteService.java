package com.dh.clinica.service;


import com.dh.clinica.excepciones.ResourceNotFoundException;
import com.dh.clinica.persistence.dto.DomicilioDTO;
import com.dh.clinica.persistence.dto.PacienteDTO;
import com.dh.clinica.persistence.model.Domicilio;
import com.dh.clinica.persistence.model.Paciente;
import com.dh.clinica.persistence.repository.IPacienteRepository;
import com.dh.clinica.service.impl.IPacienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PacienteService implements IPacienteService {
    @Autowired
    private IPacienteRepository pacienteRepository;

    @Autowired
    private DomicilioService domicilioService;

    @Autowired
    ObjectMapper mapper;

    private static final Logger logger = Logger.getLogger(Logger.class);

    @Override
    public PacienteDTO crearPaciente(PacienteDTO pacienteDTO) {
        Paciente p = mapper.convertValue(pacienteDTO, Paciente.class);
        Paciente nuevoP = pacienteRepository.save(p);
        logger.info("Paciente con ID: " + nuevoP.getId() + " guardado.");
        return mapper.convertValue(nuevoP, PacienteDTO.class);
    }

    @Override
    public PacienteDTO buscarPorId(Integer id) throws ResourceNotFoundException {
        logger.info("Buscando el paciente con ID: " + id + ".");
        Optional<Paciente> p = pacienteRepository.findById(id);
        PacienteDTO pDTO = null;
        if (p.isPresent()){
            pDTO = mapper.convertValue(p, PacienteDTO.class);
            logger.info("Paciente con ID: " + id + " encontrado.");
        } else {
            logger.error("No se encontro paciente con ID: " + id + ".");
            throw new ResourceNotFoundException("No se encontro paciente con ID: " + id + ".");
        }
        return pDTO;
    }

    @Override
    public PacienteDTO modificarPaciente(PacienteDTO pacienteDTO) throws ResourceNotFoundException {
        PacienteDTO p = buscarPorId(pacienteDTO.getId());
        DomicilioDTO d = null;

        if (pacienteDTO.getApellido() != null) {
            logger.info("Modificando el apellido del paciente con ID: " + pacienteDTO.getId() + ".");
            p.setApellido(pacienteDTO.getApellido());
        }
        if (pacienteDTO.getNombre() != null) {
            logger.info("Modificando el nombre del paciente con ID: " + pacienteDTO.getId() + ".");
            p.setNombre(pacienteDTO.getNombre());
        }
        if (pacienteDTO.getFechaIngreso() != null) {
            logger.info("Modificando la fecha de ingreso del paciente con ID: " + pacienteDTO.getId() + ".");
            p.setFechaIngreso(pacienteDTO.getFechaIngreso());
        }
        if (pacienteDTO.getDomicilio() != null && pacienteDTO.getDomicilio().getId() != null) {
            logger.info("Modificando el domicilio del paciente con ID: " + pacienteDTO.getId() + ".");
            d = domicilioService.buscarPorId(pacienteDTO.getDomicilio().getId());
            Domicilio dNuevo = mapper.convertValue(d, Domicilio.class);
            p.setDomicilio(dNuevo);
        }
        if (pacienteDTO.getDni() != null) {
            logger.info("Modificando el DNI del paciente con ID: " + pacienteDTO.getId() + ".");
            p.setDni(pacienteDTO.getDni());
        }

        return crearPaciente(p);
    }

    @Override
    public String eliminarPaciente(Integer id) throws ResourceNotFoundException {
        if (buscarPorId(id) != null){
            pacienteRepository.deleteById(id);
            logger.info("El paciente con id " + id + " ha sido eliminado.");
            return "El paciente con id " + id + " ha sido eliminado.";
        }
        logger.info("El paciente con id " + id + " no fue encontrado.");
        return "El paciente con id " + id + " no fue encontrado.";

    }

    @Override
    public Set<PacienteDTO> buscarTodos() {
        logger.info("Listando todos los pacientes.");
        List<Paciente> pacienteList = pacienteRepository.findAll();

        Set<PacienteDTO> pacienteDTOList = new HashSet<PacienteDTO>();

        PacienteDTO pDTO = null;

        for (Paciente p: pacienteList){
            pDTO = mapper.convertValue(p, PacienteDTO.class);
            pacienteDTOList.add(pDTO);
        }

        return pacienteDTOList;
    }

}
