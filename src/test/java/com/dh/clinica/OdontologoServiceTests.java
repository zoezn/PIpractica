package com.dh.clinica;

import com.dh.clinica.excepciones.ResourceNotFoundException;
import com.dh.clinica.persistence.dto.OdontologoDTO;
import com.dh.clinica.persistence.model.Odontologo;
import com.dh.clinica.service.OdontologoService;

import org.junit.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OdontologoServiceTests {

    @Autowired
    private OdontologoService odontologoService;

    @Test
    @Order(1)
    public void cargarOdontologos(){
        OdontologoDTO o = new OdontologoDTO();
        o.setNombre("Profe");
        o.setApellido("Peter");
        o.setMatricula(123);
        o = odontologoService.crearOdontologo(o);

        OdontologoDTO o2 = new OdontologoDTO();
        o2.setNombre("Zoe");
        o2.setApellido("Jimenez");
        o2.setMatricula(222);
        o2 = odontologoService.crearOdontologo(o2);

        OdontologoDTO o3 = new OdontologoDTO();
        o3.setNombre("Porfavor");
        o3.setApellido("Dios");
        o3.setMatricula(222);
        o3 = odontologoService.crearOdontologo(o3);

        Assert.assertTrue(o.getId() != null && o2.getId() != null && o3.getId() != null);
    }

    @Test
    @Order(2)
    public void traerOdontologoPorID() throws ResourceNotFoundException {
        OdontologoDTO o = null;
        o = odontologoService.buscarPorId(1);
        Assert.assertTrue(o != null);
    }

    @Test
    @Order(3)
    public void listarOdontologos() {

        Assert.assertTrue(odontologoService.buscarTodos() != null);
    }

    @Test
    @Order(4)
    public void modificarOdontologo() throws ResourceNotFoundException {
        OdontologoDTO o = new OdontologoDTO();
        o.setId(2);
        o.setMatricula(999);
        OdontologoDTO oModificado = odontologoService.modificarOdontologo(o);
        Assert.assertTrue(oModificado.getId().equals(2) && oModificado.getMatricula().equals(999));
    }


    @Test
    @Order(5)
    public void eliminarOdontologoPorId() throws ResourceNotFoundException {

        String e = odontologoService.eliminarOdontologo(3);
        Assert.assertTrue(e.equals("El odontologo con id 3 ha sido eliminado."));
    }

}
