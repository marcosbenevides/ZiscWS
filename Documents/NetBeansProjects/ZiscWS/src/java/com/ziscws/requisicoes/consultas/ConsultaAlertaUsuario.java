/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.requisicoes.consultas;

import com.ziscws.dao.AlertaDAO;
import com.ziscws.dao.LogLoginDAO;
import com.ziscws.dao.UsuarioDAO;
import com.ziscws.entidades.LogLogin;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author Marcos Benevides
 */
@Path("/consultaAlertaUsuario/{id}")
public class ConsultaAlertaUsuario {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response alertasUsuario(@Context HttpHeaders httpHeaders, @PathParam("id") int idUsuario) {
        AlertaDAO dao = new AlertaDAO();
        LogLoginDAO log = new LogLoginDAO();
        UsuarioDAO userDao = new UsuarioDAO();

        String aut = httpHeaders.getHeaderString("Authorization");
        System.out.println(aut);
        if (log.isUUIDValid(aut)) {
            return Response
                    .ok(dao.buscaAlertaUsuario(userDao.buscaUsuario(new Long(idUsuario))))
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Credentials", "true")
                    .header("Access-Control-Allow-Headers",
                            "origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Methods",
                            "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                    .build();
        }
        return Response
                .status(Status.UNAUTHORIZED)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers",
                        "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .build();
    }
}
