/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 *
 * @author Avanti Premium
 */
@Entity
@Table(name = "DPTO_POLICIA", schema = "dbo")
public class DptoPolicia implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DPTO_POLICIA")
    private long id;
    
    @Column(name = "NOME_DPTO", length = 200)
    private String nome;
    @Column(name = "ENDERECO_DPTO", length = 200)
    private String endereco;
    @Column(name = "NUMERO_DPTO", length = 200)
    private String numero;
    @Column(name = "BAIRRO_DPTO", length = 10)
    private String bairro;
    @Column(name = "CIDADE_DPTO", length = 50)
    private String cidade;
    @Column(name = "DDD_DPTO", length = 5)
    private String ddd;
    @Column(name = "TELEFONE_DPTO", length = 20)
    private String telefone;
    @Column(name = "E_MAIL_DPTO", length = 100)
    private String email;
    @Column(name = "SITE_DPTO", length = 100)
    private String site;
    @Column(name = "BASE_FIXA")
    private Boolean baseFixa;
    @Column(name = "LATITUDE")
    private String latitude;
    @Column(name = "LONGITUDE")
    private String longitude;

    public DptoPolicia() {
    }

    public DptoPolicia(String nome, String endereco, String numero, String bairro, String cidade, String ddd, String telefone, String email, String site, Boolean baseFixa, String latitude, String longitude) {
        this.nome = nome;
        this.endereco = endereco;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.ddd = ddd;
        this.telefone = telefone;
        this.email = email;
        this.site = site;
        this.baseFixa = baseFixa;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public DptoPolicia(long id, String nome, String endereco, String numero, String bairro, String cidade, String ddd, String telefone, String email, String site, Boolean baseFixa, String latitude, String longitude) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.ddd = ddd;
        this.telefone = telefone;
        this.email = email;
        this.site = site;
        this.baseFixa = baseFixa;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    
    @Override
    public String toString() {
        return "DptoPolicia{" + "id=" + id + ", nome=" + nome + ", endereco=" + endereco + ", numero=" + numero + ", bairro=" + bairro + ", cidade=" + cidade + ", ddd=" + ddd + ", telefone=" + telefone + ", email=" + email + ", site=" + site + ", baseFixa=" + baseFixa + '}';
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Boolean getBaseFixa() {
        return baseFixa;
    }

    public void setBaseFixa(Boolean baseFixa) {
        this.baseFixa = baseFixa;
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
    
    
}
