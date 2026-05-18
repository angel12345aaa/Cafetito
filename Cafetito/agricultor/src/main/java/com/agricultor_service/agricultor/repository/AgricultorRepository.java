package com.agricultor_service.agricultor.repository;

import com.agricultor_service.agricultor.model.Agricultor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgricultorRepository extends JpaRepository<Agricultor, Long> {
}