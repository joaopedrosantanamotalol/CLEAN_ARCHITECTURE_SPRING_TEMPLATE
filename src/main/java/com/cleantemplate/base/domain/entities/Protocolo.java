package com.cleantemplate.base.domain.entities;

import java.time.LocalDateTime;

public class Protocolo {

    private Long id;
    private Long ident;

    private String desc;
    private String produto;
    private String numeroMkt;

    private LocalDateTime datacriacao;

    private MarketEnum marketplace;

    private Usuario user;

    public Protocolo(Long id, Long ident, String desc, String produto, String numeroMkt, LocalDateTime datacriacao,
            MarketEnum marketplace, Usuario user) {
        this.id = id;
        this.ident = ident;
        this.desc = desc;
        this.produto = produto;
        this.numeroMkt = numeroMkt;
        this.datacriacao = datacriacao;
        this.marketplace = marketplace;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdent() {
        return ident;
    }

    public void setIdent(Long ident) {
        this.ident = ident;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getNumeroMkt() {
        return numeroMkt;
    }

    public void setNumeroMkt(String numeroMkt) {
        this.numeroMkt = numeroMkt;
    }

    public LocalDateTime getDatacriacao() {
        return datacriacao;
    }

    public void setDatacriacao(LocalDateTime datacriacao) {
        this.datacriacao = datacriacao;
    }

    public MarketEnum getMarketplace() {
        return marketplace;
    }

    public void setMarketplace(MarketEnum marketplace) {
        this.marketplace = marketplace;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    

}
