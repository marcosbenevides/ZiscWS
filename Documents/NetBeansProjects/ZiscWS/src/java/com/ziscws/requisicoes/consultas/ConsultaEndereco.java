/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.requisicoes.consultas;

import com.ziscws.dao.EnderecoDAO;
import com.ziscws.dao.UsuarioDAO;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Avanti Premium
 */
@Path("/consultaendereco/")
public class ConsultaEndereco {
    
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String bucaEndereco(@FormParam("id") String idusuario){
        
        EnderecoDAO dao = new EnderecoDAO();
        UsuarioDAO udao = new UsuarioDAO();
        
        return dao.buscaEnderecoUsuario(udao.buscaUsuario(new Long(idusuario)));
        
    }
    
}
