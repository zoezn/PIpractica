package com.dh.clinica.controller;

import com.dh.clinica.excepciones.ResourceNotFoundException;
import com.dh.clinica.persistence.dto.OdontologoDTO;
import com.dh.clinica.persistence.model.Odontologo;

import com.dh.clinica.service.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;


@RestController
@RequestMapping("/odontologos")
public class OdontologoController {
    @Autowired
    private OdontologoService odontologoService;

    private static final Logger logger = Logger.getLogger(Logger.class);


    @PostMapping("/new")
    public ResponseEntity<OdontologoDTO> registrarOdontologo(@RequestBody OdontologoDTO odontologo) {
        logger.info("Creando un nuevo odontologo.");
        return ResponseEntity.ok(odontologoService.crearOdontologo(odontologo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OdontologoDTO> buscar(@PathVariable Integer id) throws ResourceNotFoundException {
        logger.info("Buscando odontologo con ID: " + id + ".");
        return ResponseEntity.ok(odontologoService.buscarPorId(id));
    }

    @PutMapping()
    public ResponseEntity<OdontologoDTO> actualizar(@RequestBody OdontologoDTO odontologo) throws ResourceNotFoundException {
        ResponseEntity<OdontologoDTO> response = null;

        if (odontologo.getId() != null && odontologoService.buscarPorId(odontologo.getId()) != null) {
            logger.info("Modificando odontologo con ID: " + odontologo.getId() + ".");
            response = ResponseEntity.ok(odontologoService.modificarOdontologo(odontologo));
        }else {
            logger.warn("No se encontro el domicilio con ID: " + odontologo.getId() + ", no se puede actualizar.");
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) throws ResourceNotFoundException {
        ResponseEntity<String> response = null;

        if (odontologoService.buscarPorId(id) != null) {
            logger.info("Eliminando odontologo con ID: " + id + ".");
            odontologoService.eliminarOdontologo(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminado");
        } else {
            logger.warn("No se encontro odontologo con ID: " + id + ", no se puede eliminar.");
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return response;
    }
    @GetMapping
    public ResponseEntity<Set<OdontologoDTO>> buscarTodos(){
        logger.info("Listando todos los odontologos.");
        return ResponseEntity.ok(odontologoService.buscarTodos());
    }



}
