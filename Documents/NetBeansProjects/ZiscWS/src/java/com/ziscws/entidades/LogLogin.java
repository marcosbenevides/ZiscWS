/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.entidades;

import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import sun.util.BuddhistCalendar;

/**
 *
 * @author Avanti Premium
 */
@Entity
@Table(name = "TBL_LOG_LOGIN", schema = "dbo")
public class LogLogin implements Serializable {

    @Id
    @Column(name = "ID_LOG")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_USUARIO", insertable = true, updatable = true)
    @Fetch(FetchMode.JOIN)
    @Cascade(CascadeType.SAVE_UPDATE)
    private Usuario usuario;
    
    @Type(type = "date")
    @Column(name = "LOG")
    private Date log;
    
    @Column(name = "IP")
    private String ip;
    
    @Column(name = "TIPO")
    private String tipo;
    
    @Column(name= "UUID")
    private String key;
    
    @Column(name="ATIVO")
    private int ativo;

    public LogLogin (Usuario usuario, Date log, String ip, String tipo) {
        this.usuario = usuario;
        this.log = log;
        this.ip = ip;
        this.tipo = tipo;
        this.key = setKey();
        this.ativo = 1;
    }

    public LogLogin(Date log, String ip, String tipo) {
        this.log = log;
        this.ip = ip;
        this.tipo = tipo;
        this.key = setKey();
        this.ativo = 1;
    }

    public LogLogin() {
    }
    

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getLog() {
        return log;
    }

    public void setLog(Date log) {
        this.log = log;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getKey() {
        return key;
    }

    public String setKey() {

        return UUID.randomUUID().toString().replace("-", "");
        
    }

    @Override
    public String toString() {
        return "LogLogin{" + "id=" + id + ", usuario=" + usuario + ", log=" + log + ", ip=" + ip + ", tipo=" + tipo + ", key=" + key + '}';
    }

    public int getAtivo() {
        return ativo;
    }

    public void setAtivo(int ativo) {
        this.ativo = ativo;
    }
    
    

}
