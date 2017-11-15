package com.ziscws.requisicoes.consultas;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.ziscws.dao.AlertaDAO;
import com.ziscws.dao.LogLoginDAO;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * WebService para consultar alertas criados de acordo com uma localidade
 *
 * @author Marcos Benevides
 */
@Path("/consultaalerta/")
public class ConsultaAlerta {

    /**
     * Método para consultar alertas de acordo com uma localidade
     *
     * @param httpHeaders
     * @param latitude
     * @param longitude
     * @return String Json dos Alertas a serem criados.
     */
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAlerta(
            @Context HttpHeaders httpHeaders,
            @FormParam("latitude") String latitude,
            @FormParam("longitude") String longitude) {

        if(new LogLoginDAO().isUUIDValid(httpHeaders.getHeaderString(HttpHeaders.AUTHORIZATION))){
             return Response.
                ok(new AlertaDAO().buscaPorLocal(longitude, latitude))
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

    /**
     * Consulta todos os alertas criados
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response todosAlertas() {
        AlertaDAO dao = new AlertaDAO();
        return Response
                .ok(dao.todosAlertas())
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers",
                        "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .build();
    }
    

}
