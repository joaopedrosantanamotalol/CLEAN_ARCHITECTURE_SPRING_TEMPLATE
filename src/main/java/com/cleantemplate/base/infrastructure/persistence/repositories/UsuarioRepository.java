package com.cleantemplate.base.infrastructure.persistence.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cleantemplate.base.infrastructure.persistence.entities.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity,Long> {
    Boolean existsByEmail(String email);
    Optional<UsuarioEntity> findByEmail(String email);
}   
