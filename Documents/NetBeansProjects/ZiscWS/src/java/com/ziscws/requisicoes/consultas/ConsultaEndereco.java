/*
 * To change this license ipAdress, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.requisicoes.consultas;

import com.ziscws.dao.EnderecoDAO;
import com.ziscws.dao.UsuarioDAO;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

/**
 *
 * @author Avanti Premium
 */
@Path("/consultaendereco/")
public class ConsultaEndereco {

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response bucaEndereco(@FormParam("id") String idusuario) {

        EnderecoDAO dao = new EnderecoDAO();
        UsuarioDAO udao = new UsuarioDAO();

        return Response
                .ok(dao.buscaEnderecoUsuario(udao.buscaUsuario(new Long(idusuario))))
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers",
                        "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .build();
    }
}
