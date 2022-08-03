package com.dh.clinica.service;


import com.dh.clinica.excepciones.ResourceNotFoundException;
import com.dh.clinica.persistence.dto.DomicilioDTO;
import com.dh.clinica.persistence.model.Domicilio;
import com.dh.clinica.persistence.repository.IDomicilioRepository;
import com.dh.clinica.service.impl.IDomicilioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DomicilioService implements IDomicilioService {
    @Autowired
    private IDomicilioRepository domicilioRepository;

    @Autowired
    ObjectMapper mapper;

    private static final Logger logger = Logger.getLogger(Logger.class);

    @Override
    public DomicilioDTO crearDomicilio(DomicilioDTO domicilioDTO) {

        Domicilio d = mapper.convertValue(domicilioDTO, Domicilio.class);
        Domicilio nuevoD = domicilioRepository.save(d);
        logger.info("Domicilio con ID: " + nuevoD.getId() + " guardado.");
        return mapper.convertValue(nuevoD, DomicilioDTO.class);

    }

    @Override
    public DomicilioDTO buscarPorId(Integer id) throws ResourceNotFoundException {
        logger.info("Buscando el domicilio con ID: " + id + ".");
        Optional<Domicilio> d = domicilioRepository.findById(id);
        DomicilioDTO dDTO = null;
        if (d.isPresent()){
            dDTO = mapper.convertValue(d, DomicilioDTO.class);
            logger.info("Domicilio con ID: " + id + " encontrado.");
        } else {
            logger.error("No se encontro domicilio con ID: " + id + ".");
            throw new ResourceNotFoundException("No se encontro domicilio con ID: " + id + ".");
        }
        return dDTO;
    }

    @Override
    public DomicilioDTO modificarDomicilio(DomicilioDTO domicilioDTO) throws ResourceNotFoundException {
        DomicilioDTO d = buscarPorId(domicilioDTO.getId());
        if (domicilioDTO.getCalle() != null) {
            logger.info("Modificando la calle del domicilio con ID: " + domicilioDTO.getId() + ".");
            d.setCalle(domicilioDTO.getCalle());
        }
        if (domicilioDTO.getLocalidad() != null) {
            logger.info("Modificando la localidad del domicilio con ID: " + domicilioDTO.getId() + ".");
            d.setLocalidad(domicilioDTO.getLocalidad());
        }
        if (domicilioDTO.getProvincia() != null) {
            logger.info("Modificando la provincia del domicilio con ID: " + domicilioDTO.getId() + ".");
            d.setProvincia(domicilioDTO.getProvincia());
        }
        if (domicilioDTO.getNumero() != null) {
            logger.info("Modificando el numero del domicilio con ID: " + domicilioDTO.getId() + ".");
            d.setNumero(domicilioDTO.getNumero());
        }

        return crearDomicilio(d);
    }

    @Override
    public String eliminarDomicilio(Integer id) throws ResourceNotFoundException {
        if (buscarPorId(id) != null){
            domicilioRepository.deleteById(id);
            logger.info("El domicilio con id " + id + " ha sido eliminado.");
            return "El domicilio con id " + id + " ha sido eliminado.";
        }
        logger.warn("El domicilio con id " + id + " no se pudo eliminar.");
        return "El domicilio con id " + id + " no se pudo eliminar.";

    }

    @Override
    public Set<DomicilioDTO> buscarTodos() {
        logger.info("Listando todos los domicilios.");
        List<Domicilio> domicilioList = domicilioRepository.findAll();

        Set<DomicilioDTO> domicilioDTOList = new HashSet<DomicilioDTO>();

        DomicilioDTO dDTO = null;

        for (Domicilio d: domicilioList){
            dDTO = mapper.convertValue(d, DomicilioDTO.class);
            domicilioDTOList.add(dDTO);
        }

        return domicilioDTOList;
    }



}
