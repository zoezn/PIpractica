package com.dh.clinica.persistence.repository;

import com.dh.clinica.persistence.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITurnoRepository extends JpaRepository <Turno, Integer>{
}
