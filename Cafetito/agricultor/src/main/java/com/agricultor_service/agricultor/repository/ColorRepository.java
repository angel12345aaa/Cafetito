package com.agricultor_service.agricultor.repository;

import com.agricultor_service.agricultor.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {}