/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.requisicoes;

import com.google.gson.Gson;
import com.ziscws.entidades.Alerta;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Marcos Benevides
 */

    @Path("/ConsultaAlerta/{latitude}/{longitude}")
public class ConsultaAlerta {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public GenericEntity<List<Alerta>> getAlerta(
            @PathParam("latitude") String latitude,
            @PathParam("longitude") String longitude) {

        Consultas consultas = new Consultas();
         Gson gson = new Gson();

        List<Alerta> lista = consultas.buscaAlerta(latitude, longitude);
            System.err.println("AQUI 2");
        
        GenericEntity<List<Alerta>> entidade = new GenericEntity<List<Alerta>>(lista){};
        
        return entidade;
        
    }
}
