/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.requisicoes;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Marcos Benevides
 */
@Path("/ConsultaAlerta/")
public class ConsultaAlerta {

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String getAlerta(
            @FormParam("latitude") String latitude,
            @FormParam("longitude") String longitude) {

        Consultas consultas = new Consultas();

        // List<Alerta> lista = consultas.buscaAlerta(latitude, longitude);
        //GenericEntity<List<Alerta>> entidade = new GenericEntity<List<Alerta>>(lista) {};

        return consultas.buscaAlerta(latitude, longitude);

    }
}
