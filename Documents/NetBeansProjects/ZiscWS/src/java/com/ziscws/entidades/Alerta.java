/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.entidades;

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
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author Avanti Premium
 */
@Entity
@Table(name = "TBL_ALERTA", schema = "dbo")
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID_ALERTA")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_USUARIO", insertable = true, updatable = true)
    @Fetch(FetchMode.JOIN)
    @Cascade(CascadeType.SAVE_UPDATE)
    private Usuario usuario;

    @Temporal(TemporalType.TIMESTAMP)
    private Date logHora;

    @Column(name = "LONGITUDE", length = 80, nullable = false)
    private String longitude;
    @Column(name = "LATITUDE", length = 80, nullable = false)
    private String latitude;
    @Column(name = "BAIRRO", length = 40, nullable = false)
    private String bairro;
    @Column(name = "CIDADE", length = 40, nullable = false)
    private String cidade;
    @Column(name = "ESTADO", length = 40, nullable = false)
    private String estado;
    @Column(name = "OBS", length = 100, nullable = false)
    private String observacao;
    @Column(name = "TIPO", length = 20, nullable = false)
    private String tipo;
    @Column(name = "E_POSITIVO", nullable = false)
    private Boolean ePositivo;
    @Column(name = "ATIVO", nullable = false)
    private Boolean ativo;

    public Alerta(Usuario usuario, Date logHora, String longitude, String latitude, String bairro, String cidade, String estado, String observacao, String tipo, Boolean ePositivo, Boolean ativo) {
        this.usuario = usuario;
        this.logHora = logHora;
        this.longitude = longitude;
        this.latitude = latitude;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.observacao = observacao;
        this.tipo = tipo;
        this.ePositivo = ePositivo;
        this.ativo = ativo;
    }

    public Alerta() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getLogHora() {
        return logHora;
    }

    public void setLogHora(Date logHora) {
        this.logHora = logHora;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Boolean getePositivo() {
        return ePositivo;
    }

    public void setePositivo(Boolean ePositivo) {
        this.ePositivo = ePositivo;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

}
