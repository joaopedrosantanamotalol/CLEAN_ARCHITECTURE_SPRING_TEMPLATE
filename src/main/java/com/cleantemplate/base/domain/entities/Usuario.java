package com.cleantemplate.base.domain.entities;

import java.time.LocalDateTime;

public class Usuario {

    private Long id;

    private String nome;

    private String senha;

    private String email;

    private RoleEnum role;

    private LocalDateTime dataCriacao;

     public Usuario() {
    }

    public Usuario(
        String nome, String email, String senha
    ){
        this.nome = nome;
        this.senha = senha;
        this.email = email;
    }

    public Usuario(Long id, String nome, String senha, String email, RoleEnum role, LocalDateTime dataCriacao) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.email = email;
        this.role = role;
        this.dataCriacao = dataCriacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    

}
