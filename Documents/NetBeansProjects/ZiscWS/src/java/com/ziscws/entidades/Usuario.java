/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.entidades;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;

/**
 *
 * @author Avanti Premium
 */
@Entity
@Table(name = "TBL_USUARIO", schema = "dbo")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private long id;

    @Column(name = "NOME", length = 50, nullable = false)
    private String nome;

    @Column(name = "EMAIL", length = 50, nullable = false, unique = true)
    private String email;

    @Column(name = "CPF", length = 15, nullable = false, unique = true)
    private String cpf;

    @Column(name = "CELULAR", length = 15, nullable = false)
    private String celular;

    @Column(name = "SENHA", length = 32, nullable = false)
    private String senha;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    transient private Set<Endereco> enderecos = new HashSet<>();

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    transient private Set<Alerta> alertas = new HashSet<>();

    public Usuario(String nome, String email, String cpf, String celular, String senha) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.celular = celular;
        this.senha = senha;
    }

    public Usuario() {
    }

    public Usuario(String nome, String email, String cpf, String celular, String senha, Alerta alerta) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.celular = celular;
        this.senha = senha;
        this.alertas.add(alerta);
    }

    public Usuario(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.cpf = usuario.getCpf();
        this.celular = usuario.getCelular();
        this.senha = usuario.getSenha();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Set<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(Endereco enderecos) {
        this.enderecos.add(enderecos);
    }

    public Set<Alerta> getAlertas() {
        return alertas;
    }

    public void setAlertas(Alerta alerta) {
        this.alertas.add(alerta);
    }

}
