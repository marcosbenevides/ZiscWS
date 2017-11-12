/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.requisicoes.consultas;

import com.ziscws.dao.AlertaDAO;
import com.ziscws.dao.UsuarioDAO;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Marcos Benevides
 */
@Path("/consultaAlertaUsuario/{id}")
public class ConsultaAlertaUsuario {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response alertasUsuario(@PathParam("id") int idUsuario) {
        AlertaDAO dao = new AlertaDAO();
        UsuarioDAO userDao = new UsuarioDAO();
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
}
