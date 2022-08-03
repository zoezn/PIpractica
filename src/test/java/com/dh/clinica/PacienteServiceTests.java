package com.dh.clinica;


import com.dh.clinica.excepciones.ResourceNotFoundException;
import com.dh.clinica.persistence.dto.DomicilioDTO;
import com.dh.clinica.persistence.dto.OdontologoDTO;
import com.dh.clinica.persistence.dto.PacienteDTO;
import com.dh.clinica.persistence.model.Domicilio;
import com.dh.clinica.persistence.model.Paciente;
import com.dh.clinica.service.DomicilioService;
import com.dh.clinica.service.OdontologoService;
import com.dh.clinica.service.PacienteService;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
 class PacienteServiceTests {

    @Autowired
    private PacienteService pacienteService;

    @Test
    @Order(1)
    public void cargarPaciente(){
        PacienteDTO p = new PacienteDTO();
        Domicilio d = new Domicilio("Rivadavia", 22, "Lanus", "Buenos Aires");
        Date date = new Date(2002-2-11);
        p.setNombre("Rosa");
        p.setApellido("Fernandez");
        p.setDomicilio(d);
        p.setDni(17);
        p.setFechaIngreso(date);
        p = pacienteService.crearPaciente(p);

        PacienteDTO p2 = new PacienteDTO();
        Domicilio d2 = new Domicilio("Quilmes", 222, "Almagro", "Buenos Aires");
        Date date2 = new Date(2010-10-11);
        p2.setNombre("Maria");
        p2.setApellido("Jimenez");
        p2.setDomicilio(d2);
        p2.setDni(222);
        p2.setFechaIngreso(date2);
        p2 = pacienteService.crearPaciente(p2);

        PacienteDTO p3 = new PacienteDTO();
        Domicilio d3 = new Domicilio("Pavon", 123, "Avellaneda", "Buenos Aires");
        Date date3 = new Date(2022-6-19);
        p3.setNombre("Zoe");
        p3.setApellido("Final");
        p3.setDomicilio(d3);
        p3.setDni(123);
        p3.setFechaIngreso(date3);
        p3 = pacienteService.crearPaciente(p3);

        Assert.assertTrue(p.getId() != null && p2.getId() != null && p3.getId() != null);
    }

    @Test
    @Order(2)
    public void traerPacientePorID() throws ResourceNotFoundException {
        PacienteDTO p = null;
        p = pacienteService.buscarPorId(1);
        Assert.assertTrue(p != null);

    }

    @Test
    @Order(3)
    public void listarPacientes() {

        Assert.assertTrue(pacienteService.buscarTodos() != null);
    }

    @Test
    @Order(4)
    public void modificarPaciente() throws ResourceNotFoundException {
        PacienteDTO p = new PacienteDTO();
        p.setId(2);
        p.setNombre("Zoe");

        PacienteDTO pModificado = pacienteService.modificarPaciente(p);
        Assert.assertTrue(pModificado.getId().equals(2) && pModificado.getNombre().equals("Zoe"));

    }

    @Test
    @Order(5)
    public void eliminarPacientePorId() throws ResourceNotFoundException {
        String e = pacienteService.eliminarPaciente(3);
        Assert.assertTrue(e.equals("El paciente con id 3 ha sido eliminado."));
    }


}
