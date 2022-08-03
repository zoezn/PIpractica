package com.dh.clinica.controller;

import com.dh.clinica.excepciones.ResourceNotFoundException;
import com.dh.clinica.persistence.dto.OdontologoDTO;
import com.dh.clinica.persistence.dto.PacienteDTO;
import com.dh.clinica.persistence.model.Odontologo;
import com.dh.clinica.persistence.model.Paciente;
import com.dh.clinica.service.PacienteService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    private static final Logger logger = Logger.getLogger(Logger.class);

    @PostMapping("/new")
    public ResponseEntity<PacienteDTO> registrarPaciente(@RequestBody PacienteDTO paciente) {
        logger.info("Creando un nuevo paciente.");
        return ResponseEntity.ok(pacienteService.crearPaciente(paciente));

    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> buscar(@PathVariable Integer id) throws ResourceNotFoundException {
        logger.info("Buscando odontologo con ID: " + id +".");
        return ResponseEntity.ok(pacienteService.buscarPorId(id));
    }

    @PutMapping()
    public ResponseEntity<PacienteDTO> actualizar(@RequestBody PacienteDTO paciente) throws ResourceNotFoundException {
        ResponseEntity<PacienteDTO> response = null;

        if (paciente.getId() != null && pacienteService.buscarPorId(paciente.getId()) != null) {
            logger.info("Modificando paciente con ID: " + paciente.getId() + ".");
            response = ResponseEntity.ok(pacienteService.modificarPaciente(paciente));
        } else {
            logger.warn("No se encontro el paciente con ID: " + paciente.getId() + ", no se puede actualizar.");
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) throws ResourceNotFoundException {
        ResponseEntity<String> response = null;

        if (pacienteService.buscarPorId(id) != null) {
            logger.info("Eliminando paciente con ID: " + id + ".");
            pacienteService.eliminarPaciente(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminado");
        } else {
            logger.warn("No se encontro el paciente con ID: " + id + ", no se puede eliminar.");
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return response;
    }

    @GetMapping
    public ResponseEntity<Set<PacienteDTO>> buscarTodos(){
        logger.info("Listando todos los pacientes.");
        return ResponseEntity.ok(pacienteService.buscarTodos());
    }


}
