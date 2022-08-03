package com.dh.clinica;

import com.dh.clinica.excepciones.ResourceNotFoundException;
import com.dh.clinica.persistence.dto.OdontologoDTO;
import com.dh.clinica.persistence.dto.PacienteDTO;
import com.dh.clinica.persistence.dto.TurnoDTO;
import com.dh.clinica.persistence.model.Domicilio;
import com.dh.clinica.persistence.model.Odontologo;
import com.dh.clinica.persistence.model.Paciente;
import com.dh.clinica.persistence.model.Turno;
import com.dh.clinica.service.OdontologoService;
import com.dh.clinica.service.PacienteService;
import com.dh.clinica.service.TurnoService;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TurnoServiceTests {
    @Autowired
    TurnoService turnoService;
    @Autowired
    PacienteService pacienteService;
    @Autowired
    OdontologoService odontologoService;


    public void cargarEntidades(){
        PacienteDTO p = new PacienteDTO();
        Domicilio d = new Domicilio("Rivadavia", 22, "Lanus", "Buenos Aires");
        Date date = new Date(2002-2-11);
        p.setNombre("Rosa");
        p.setApellido("Fernandez");
        p.setDomicilio(d);
        p.setDni(17);
        p.setFechaIngreso(date);
        pacienteService.crearPaciente(p);

        System.out.println("____________");

        OdontologoDTO o = new OdontologoDTO();
        o.setNombre("Profe");
        o.setApellido("Peter");
        o.setMatricula(123);
        odontologoService.crearOdontologo(o);
    }


    @Test
    @Order(1)
    public void cargarTurno() throws ResourceNotFoundException {
        cargarEntidades();
        System.out.println("____________");
        TurnoDTO t = new TurnoDTO();
        Paciente p = new Paciente();
        Odontologo d = new Odontologo();
        p.setId(1);
        d.setId(1);
        t.setPaciente(p);
        t.setOdontologo(d);
        Date date = new Date(2022-8-12);
        t.setFecha(date);

        t = turnoService.crearTurno(t);
        Assert.assertTrue(t.getId() != null);
        System.out.println("____________");
    }

    @Test
    @Order(2)
    public void traerTurnoPorID() throws ResourceNotFoundException {
        TurnoDTO t = null;
        t = turnoService.buscarPorId(1);
        Assert.assertTrue(t != null);
    }


    @Test
    @Order(3)
    public void listarTurnos() {

        Assert.assertTrue(turnoService.buscarTodos() != null);
    }

    @Test
    @Order(4)
    public void eliminarTurnoPorId() throws ResourceNotFoundException {
        String e = turnoService.eliminarTurno(1);
        Assert.assertTrue(e.equals("El turno con id 1 ha sido eliminado."));
    }

}
