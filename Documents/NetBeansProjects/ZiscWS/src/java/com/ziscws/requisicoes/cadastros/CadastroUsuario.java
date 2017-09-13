/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.requisicoes.cadastros;

import com.google.gson.Gson;
import com.ziscws.dao.UsuarioDAO;
import com.ziscws.entidades.Usuario;
import java.io.IOException;
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
@Path("/cadastrousuario/")
public class CadastroUsuario {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String cadastrar(@FormParam("nome") String nome,
            @FormParam("email") String email,
            @FormParam("cpf") String cpf,
            @FormParam("celular") String celular,
            @FormParam("senha") String senha) throws IOException {

        Gson gson = new Gson();
        UsuarioDAO dao = new UsuarioDAO();
        Usuario usuario;
        if (dao.usuarioCadastrado(email)) {
            String json = gson.toJson("Email já cadastrado!");
            return json;
        }
        String senhaCrip = dao.md5Converte(senha);
        usuario = new Usuario(nome, email, cpf, celular, senhaCrip);
        return dao.novoUsuario(usuario);
    }
}
