/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.requisicoes.consultas;

import com.ziscws.dao.UsuarioDAO;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
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
@Path("/login/")
public class LoginCrip {

    /**
     * Autentica usuário.
     *
     * @param email
     * @param password
     * @return Dados não sensiveis do usuario logado ou vazio quando a
     * autenticação falha.
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws CloneNotSupportedException
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String loginCrip(@FormParam("email") String email,
            @FormParam("password") String password) throws NoSuchAlgorithmException, UnsupportedEncodingException, CloneNotSupportedException {

        UsuarioDAO dao = new UsuarioDAO();

        email = new String(Base64.getDecoder().decode(email));
        password = new String(Base64.getDecoder().decode(password));

        password = dao.md5Converte(password);

        return dao.login(email, password);
    }

}
