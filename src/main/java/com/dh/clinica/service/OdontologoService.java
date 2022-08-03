package com.dh.clinica.service;

import com.dh.clinica.excepciones.ResourceNotFoundException;
import com.dh.clinica.persistence.dto.OdontologoDTO;
import com.dh.clinica.persistence.model.Odontologo;
import com.dh.clinica.persistence.repository.IOdontologoRepository;
import com.dh.clinica.service.impl.IOdontologoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OdontologoService implements IOdontologoService {

    @Autowired
    private IOdontologoRepository odontologoRepository;

    @Autowired
    ObjectMapper mapper;

    private static final Logger logger = Logger.getLogger(Logger.class);

    @Override
    public OdontologoDTO crearOdontologo(OdontologoDTO odontologoDTO) {
        Odontologo o = mapper.convertValue(odontologoDTO, Odontologo.class);
        Odontologo nuevoO = odontologoRepository.save(o);
        logger.info("Odontologo con ID: " + nuevoO.getId() + " guardado.");
        return mapper.convertValue(nuevoO, OdontologoDTO.class);
    }

    @Override
    public OdontologoDTO buscarPorId(Integer id) throws ResourceNotFoundException {
        logger.info("Buscando el odontologo con ID: " + id + ".");
        Optional<Odontologo> o = odontologoRepository.findById(id);
        OdontologoDTO oDTO = null;
        if (o.isPresent()){
            oDTO = mapper.convertValue(o, OdontologoDTO.class);
            logger.info("Odontologo con ID: " + id + " encontrado.");
            return oDTO;
        } else {
            logger.error("No se encontro odontologo con ID: " + id + ".");
            throw new ResourceNotFoundException("No se encontro odontologo con ID: " + id + ".");
        }

    }

    @Override
    public OdontologoDTO modificarOdontologo(OdontologoDTO odontologoDTO) throws ResourceNotFoundException {
        OdontologoDTO o = buscarPorId(odontologoDTO.getId());
        if (odontologoDTO.getNombre() != null){
            logger.info("Modificando el nombre del odontologo con ID: " + odontologoDTO.getId() + ".");
            o.setNombre(odontologoDTO.getNombre());
        }
        if (odontologoDTO.getApellido() != null){
            logger.info("Modificando el apellido del odontologo con ID: " + odontologoDTO.getId() + ".");
            o.setApellido(odontologoDTO.getApellido());
        }
        if (odontologoDTO.getMatricula() != null){
            logger.info("Modificando la matricula del odontologo con ID: " + odontologoDTO.getId() + ".");
            o.setMatricula(odontologoDTO.getMatricula());
        }

        return crearOdontologo(o);
    }

    @Override
    public String eliminarOdontologo(Integer id) throws ResourceNotFoundException {
        if (buscarPorId(id) != null){
            odontologoRepository.deleteById(id);
            logger.info("El odontologo con id " + id + " ha sido eliminado.");
            return "El odontologo con id " + id + " ha sido eliminado.";
        }
        logger.warn("El odontologo con id " + id + " no se pudo eliminar.");
        return "El odontologo con id " + id + " no se pudo eliminar.";

    }

    @Override
    public Set<OdontologoDTO> buscarTodos() {
        logger.info("Listando todos los odontologos.");
        List<Odontologo> odontologoList = odontologoRepository.findAll();

        Set<OdontologoDTO> odontologoDTOList = new HashSet<OdontologoDTO>();

        OdontologoDTO oDTO = null;

        for (Odontologo o: odontologoList){
            oDTO = mapper.convertValue(o, OdontologoDTO.class);
            odontologoDTOList.add(oDTO);
        }

        return odontologoDTOList;
    }


}
