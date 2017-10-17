/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author Avanti Premium
 */
@Entity
@Table(name = "CALL_HANDLER", schema = "dbo")
public class CallHandler implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CALL_HANDLER")
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_USUARIO", insertable = true, updatable = true)
    @Fetch(FetchMode.JOIN)
    @Cascade(CascadeType.ALL)
    private Usuario usuario;
    
    @Column(name = "LATITUDE", length = 200)
    private String latitude;
    @Column(name = "LONGITUDE", length = 200)
    private String longitude;
    @Column(name = "CIDADE", length = 50)
    private String cidade;
    @Column(name = "BAIRRO", length = 50)
    private String bairro;
    @Column(name = "ESTADO", length = 200)
    private String estado;
    @Column(name = "ATIVO")
    private boolean ativo;
    
    @Column(name = "LOG")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date log;

    public CallHandler() {
    }

    public CallHandler(Long id, Usuario usuario, String latitude, String longitude, String cidade, String bairro, String estado, boolean ativo, Date logHora) {
        this.id = id;
        this.usuario = usuario;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cidade = cidade;
        this.bairro = bairro;
        this.estado = estado;
        this.ativo = ativo;
        this.log = logHora;
    }

    public CallHandler(Usuario usuario, String latitude, String longitude, String cidade, String bairro, String estado, boolean ativo, Date logHora) {
        this.usuario = usuario;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cidade = cidade;
        this.bairro = bairro;
        this.estado = estado;
        this.ativo = ativo;
        this.log = logHora;
    }

    @Override
    public String toString() {
        return "CallHandler{" + "id=" + id + ", usuario=" + usuario + ", latitude=" + latitude + ", longitude=" + longitude + ", cidade=" + cidade + ", bairro=" + bairro + ", estado=" + estado + ", ativo=" + ativo + ", logHora=" + log + '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Date getLog() {
        return log;
    }

    public void setLog(Date logHora) {
        this.log = logHora;
    }
    
    
    
    
}
