package com.cleantemplate.base.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cleantemplate.base.infrastructure.persistence.entities.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity,Long> {
    boolean existsByEmail(String email);
}   
