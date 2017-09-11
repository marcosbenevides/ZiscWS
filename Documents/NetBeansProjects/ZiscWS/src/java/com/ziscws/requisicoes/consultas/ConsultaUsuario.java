/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.requisicoes.consultas;

import com.google.gson.Gson;
import com.ziscws.entidades.Usuario;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.hibernate.LazyInitializationException;

/**
 *
 * @author mikef
 */
@Path("/usuario/{email}")
public class ConsultaUsuario {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String buscausuario(@PathParam ("email") String email) {

        Consultas con = new Consultas();
        Gson gson = new Gson();
        System.err.println("Criado Gson");
        Usuario usuario = con.buscaUsuario(email);
        String json = null;
        try {
            json = gson.toJson(usuario);
        } catch (LazyInitializationException | StackOverflowError ex) {
            System.err.print("Erro na porra toda.");
        }
        System.err.println("Criado objeto Json");

        return json;
    }

}
