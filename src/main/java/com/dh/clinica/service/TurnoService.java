package com.dh.clinica.service;

import com.dh.clinica.excepciones.ResourceNotFoundException;
import com.dh.clinica.persistence.dto.OdontologoDTO;
import com.dh.clinica.persistence.dto.PacienteDTO;
import com.dh.clinica.persistence.dto.TurnoDTO;
import com.dh.clinica.persistence.model.Odontologo;
import com.dh.clinica.persistence.model.Paciente;
import com.dh.clinica.persistence.model.Turno;
import com.dh.clinica.persistence.repository.ITurnoRepository;
import com.dh.clinica.service.impl.ITurnoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TurnoService implements ITurnoService {

    @Autowired
    private ITurnoRepository turnoRepository;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;
    @Autowired
    ObjectMapper mapper;

    private static final Logger logger = Logger.getLogger(Logger.class);

    @Override
    public TurnoDTO crearTurno(TurnoDTO turnoDTO) throws ResourceNotFoundException {
        Integer pID = turnoDTO.getPaciente().getId();
        Integer oID = turnoDTO.getOdontologo().getId();

        if (pacienteService.buscarPorId(pID) != null){
            turnoDTO.setPaciente(mapper.convertValue(pacienteService.buscarPorId(pID), Paciente.class));
        }

        if (odontologoService.buscarPorId(oID) != null){
            turnoDTO.setOdontologo(mapper.convertValue(odontologoService.buscarPorId(oID), Odontologo.class));
        }


        Turno t = mapper.convertValue(turnoDTO, Turno.class);

        Turno t2 = turnoRepository.save(t);

        logger.info("Turno con ID: " + t2.getId() + " guardado.");
        return mapper.convertValue(t2, TurnoDTO.class);
    }

    @Override
    public TurnoDTO buscarPorId(Integer id) throws ResourceNotFoundException {
        logger.info("Buscando el turno con ID: " + id + ".");
        Optional<Turno> t = turnoRepository.findById(id);
        TurnoDTO tDTO= null;
        if (t.isPresent()){
            tDTO = mapper.convertValue(t, TurnoDTO.class);
            logger.info("Turno con ID: " + id + " encontrado.");
        } else {
            logger.error("No se encontro paciente con ID: " + id + ".");
            throw new ResourceNotFoundException("No se encontro turno con ID: " + id + ".");
        }
        return tDTO;
    }

    @Override
    public TurnoDTO modificarTurno(TurnoDTO turnoDTO) throws ResourceNotFoundException {
        TurnoDTO t = buscarPorId(turnoDTO.getId());
        PacienteDTO p = null;
        OdontologoDTO o = null;

        if (turnoDTO.getOdontologo() != null && turnoDTO.getOdontologo().getId() != null ){
            logger.info("Modificando el odontologo del turno con ID: " + turnoDTO.getId() + ".");
            o = odontologoService.buscarPorId(turnoDTO.getOdontologo().getId());
            Odontologo oNuevo = mapper.convertValue(o, Odontologo.class);
            t.setOdontologo(oNuevo);
        }
        if (turnoDTO.getPaciente() != null && turnoDTO.getPaciente().getId() != null){
            logger.info("Modificando el paciente del turno con ID: " + turnoDTO.getId() + ".");
            p = pacienteService.buscarPorId(turnoDTO.getPaciente().getId());
            Paciente pNuevo = mapper.convertValue(p, Paciente.class);
            t.setPaciente(pNuevo);
        }
        if (turnoDTO.getFecha() != null){
            logger.info("Modificando la fecha del turno con ID: " + turnoDTO.getId() + ".");
            t.setFecha(turnoDTO.getFecha());
        }


        return crearTurno(t);
    }

    @Override
    public String eliminarTurno(Integer id) throws ResourceNotFoundException {
        if (buscarPorId(id) != null){
            turnoRepository.deleteById(id);
            logger.info("El turno con id " + id + " ha sido eliminado.");
            return "El turno con id " + id + " ha sido eliminado.";
        }
        logger.warn("El turno con id " + id + " no fue encontrado.");
        return "El turno con id " + id + " no fue encontrado.";
    }

    @Override
    public Set<TurnoDTO> buscarTodos() {
        logger.info("Listando todos los turnos.");
        List<Turno> turnoList = turnoRepository.findAll();
        Set<TurnoDTO> turnoDTOList = new HashSet<TurnoDTO>();
        TurnoDTO tDTO = null;

        for (Turno t : turnoList){
            tDTO = mapper.convertValue(t, TurnoDTO.class);
            turnoDTOList.add(tDTO);
        }
        return turnoDTOList;

    }

}
