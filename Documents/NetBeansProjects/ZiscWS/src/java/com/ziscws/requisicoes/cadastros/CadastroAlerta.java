/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.requisicoes.cadastros;

import com.ziscws.dao.AlertaDAO;
import com.ziscws.dao.UsuarioDAO;
import com.ziscws.entidades.Alerta;
import com.ziscws.entidades.Usuario;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Avanti Premium
 */
@Path("/cadastroalerta/")
public class CadastroAlerta {

    /**
     * Cadastra novos alertas por POST
     *
     * @param idusuario
     * @param logHora
     * @param longitude
     * @param latitude
     * @param bairro
     * @param cidade
     * @param estado
     * @param observacao
     * @param tipo
     * @param ePositivo
     * @return String json com o alerta criado
     */
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response novoAlerta(@FormParam("id") String idusuario,
            @FormParam("logHora") Date logHora,
            @FormParam("longitude") String longitude,
            @FormParam("latitude") String latitude,
            @FormParam("bairro") String bairro,
            @FormParam("cidade") String cidade,
            @FormParam("estado") String estado,
            @FormParam("observacao") String observacao,
            @FormParam("tipo") String tipo,
            @FormParam("ePositivo") Boolean ePositivo) {

        AlertaDAO daoA = new AlertaDAO();
        Alerta alerta = new Alerta(logHora, longitude, latitude, bairro,
                cidade, estado, observacao, tipo, ePositivo, true);
        
        return Response
                .ok(daoA.novoAlerta(alerta, new Long(idusuario)))
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
