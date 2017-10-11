package com.ziscws.requisicoes.consultas;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.google.gson.Gson;
import com.ziscws.dao.AlertaDAO;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
     * @param latitude
     * @param longitude
     * @return String Json dos Alertas a serem criados.
     */
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAlerta(
            @FormParam("latitude") String latitude,
            @FormParam("longitude") String longitude) {

        AlertaDAO dao = new AlertaDAO();

        return Response.
                ok(dao.buscaPorLocal(longitude, latitude))
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers",
                        "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .entity("")
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
                .entity("")
                .build();
    }
}
