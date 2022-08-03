package com.dh.clinica.persistence.repository;

import com.dh.clinica.persistence.model.Domicilio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDomicilioRepository extends JpaRepository <Domicilio, Integer> {
}
