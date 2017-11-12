/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.requisicoes.cadastros;

import com.google.gson.Gson;
import com.ziscws.dao.CallHandlerDAO;
import com.ziscws.dao.UsuarioDAO;
import com.ziscws.entidades.CallHandler;
import com.ziscws.entidades.Usuario;
import java.util.Date;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Classe para gerencia de chamadas efetuadas pelo cliente no android e disponibilizada
 * para o dashboard da policia
 * @author Avanti Premium
 */
@Path("/callhandler/")
public class NewCallHandler {

    /**
     * Cria um registro de chamada no banco de dados.
     * @param id_usuario
     * @param latitude
     * @param longitude
     * @param cidade
     * @param bairro
     * @param estado
     * @return call completa.
     */
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response novaCall(@FormParam("id_usuario") Long id_usuario,
            @FormParam("latitude") String latitude,
            @FormParam("longitude") String longitude,
            @FormParam("cidade") String cidade,
            @FormParam("bairro") String bairro,
            @FormParam("estado") String estado) {

        UsuarioDAO dao = new UsuarioDAO();
        CallHandlerDAO callHandler = new CallHandlerDAO();

        Usuario usuario = dao.buscaUsuario(id_usuario);

        CallHandler call = new CallHandler(usuario,
                latitude, longitude, cidade, bairro, estado, true,
                new Date(System.currentTimeMillis()));

        return Response.
                ok(callHandler.setCallHandler(call))
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers",
                        "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .build();
    }

    /**
     * Função para processar todas as calls ativas
     * @return lista de calls ativas
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCalls() {

        CallHandlerDAO dao = new CallHandlerDAO();

        return Response.
                ok(dao.getCall())
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers",
                        "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .build();
    }

    /**
     * Chama função para alterar o status da call para inativa
     * @param id_call
     * @return call modificada
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response killCall(@FormParam("id") Long id_call) {

        Gson gson = new Gson();
        CallHandlerDAO dao = new CallHandlerDAO();
        CallHandler call = gson.fromJson(dao.getCall(id_call), CallHandler.class);
        call.setAtivo(false);

        return Response.
                ok(dao.setCallHandler(call))
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers",
                        "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .build();
    }

}
