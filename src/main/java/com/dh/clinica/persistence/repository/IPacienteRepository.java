package com.dh.clinica.persistence.repository;

import com.dh.clinica.persistence.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPacienteRepository extends JpaRepository <Paciente, Integer>{
}
