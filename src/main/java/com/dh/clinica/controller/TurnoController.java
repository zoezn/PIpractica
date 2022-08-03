package com.dh.clinica.controller;

import com.dh.clinica.excepciones.ResourceNotFoundException;
import com.dh.clinica.persistence.dto.PacienteDTO;
import com.dh.clinica.persistence.dto.TurnoDTO;
import com.dh.clinica.persistence.model.Turno;
import com.dh.clinica.service.OdontologoService;
import com.dh.clinica.service.PacienteService;
import com.dh.clinica.service.TurnoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    @Autowired
    private TurnoService turnoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;

    private static final Logger logger = Logger.getLogger(Logger.class);

    @PostMapping("/new")
    public ResponseEntity<TurnoDTO> registrarTurno(@RequestBody TurnoDTO turno) throws ResourceNotFoundException {
        ResponseEntity<TurnoDTO> response;
        if (pacienteService.buscarPorId(turno.getPaciente().getId()) != null && odontologoService.buscarPorId(turno.getOdontologo().getId()) != null){
            response = ResponseEntity.ok(turnoService.crearTurno(turno));
            logger.info("Creando un nuevo turno.");

        }else {
            logger.warn("No se pudo crear el turno.");
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return response;


    }


    @GetMapping
    public ResponseEntity<Set<TurnoDTO>> listar() {
        logger.info("Listando todos los turnos.");
        return ResponseEntity.ok(turnoService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoDTO> buscar(@PathVariable Integer id) throws ResourceNotFoundException {
        logger.info("Buscando turno con ID: " + id +".");
        return ResponseEntity.ok(turnoService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) throws ResourceNotFoundException {
        ResponseEntity<String> response;
        if (turnoService.buscarPorId(id) != null) {
            logger.info("Eliminando turno con ID: " + id + ".");
            turnoService.eliminarTurno(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminado");
        } else {
            logger.warn("No se encontro el turno con ID: " + id + ", no se puede eliminar.");
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

    @PutMapping
    public ResponseEntity<TurnoDTO> actualizarTurno(@RequestBody TurnoDTO turno) throws ResourceNotFoundException {
        logger.info("Modificando turno con ID: " + turno.getId() + ".");
        return ResponseEntity.ok(turnoService.modificarTurno(turno));

    }


}
