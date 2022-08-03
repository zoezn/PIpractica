package com.dh.clinica.controller;

import com.dh.clinica.excepciones.ResourceNotFoundException;
import com.dh.clinica.persistence.dto.DomicilioDTO;
import com.dh.clinica.persistence.dto.OdontologoDTO;
import com.dh.clinica.persistence.model.Domicilio;
import com.dh.clinica.persistence.model.Odontologo;
import com.dh.clinica.persistence.model.Paciente;
import com.dh.clinica.service.DomicilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;

@RestController
@RequestMapping("/domicilios")
public class DomicilioController {
    @Autowired
    private DomicilioService domicilioService;

    private static final Logger logger = Logger.getLogger(Logger.class);

    @GetMapping("/{id}")
    public ResponseEntity<DomicilioDTO> buscar(@PathVariable Integer id) throws ResourceNotFoundException {
        DomicilioDTO domicilio = domicilioService.buscarPorId(id);
        logger.info("Buscando domicilio con ID: " + id+ ".");
        return ResponseEntity.ok(domicilio);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) throws ResourceNotFoundException {
        ResponseEntity<String> response = null;

        if (domicilioService.buscarPorId(id) != null) {
            domicilioService.eliminarDomicilio(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminado");
            logger.info("Eliminando domicilio con ID: " + id+ ".");
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            logger.warn("No se encontro domicilio con ID: " + id + ", no se puede eliminar"+ ".");
        }

        return response;
    }

    @PutMapping("/update")
    public ResponseEntity<DomicilioDTO> actualizar(@RequestBody DomicilioDTO domicilio) throws ResourceNotFoundException {
        ResponseEntity<DomicilioDTO> response = null;

        if (domicilio.getId() != null && domicilioService.buscarPorId(domicilio.getId()) != null) {
            logger.info("Actualizando domicilio con ID: " + domicilio.getId() + ".");
            response = ResponseEntity.ok(domicilioService.modificarDomicilio(domicilio));

        }else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            logger.warn("No se encontro domicilio con ID: " + domicilio.getId() + ", no se puede actualizar.");
        }
        return response;
    }


    @GetMapping
    public ResponseEntity<Set<DomicilioDTO>> buscarTodos(){
        logger.info("Listando todos los domicilios.");
        return ResponseEntity.ok(domicilioService.buscarTodos());
    }


}
