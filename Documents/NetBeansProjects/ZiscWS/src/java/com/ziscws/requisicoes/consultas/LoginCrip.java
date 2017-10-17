/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.requisicoes.consultas;

import com.ziscws.dao.LogLoginDAO;
import com.ziscws.dao.UsuarioDAO;
import com.ziscws.entidades.LogLogin;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.Base64;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
     * @param request
     * @return Dados não sensiveis do usuario logado ou vazio quando a
     * autenticação falha.
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws CloneNotSupportedException
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response loginCrip(@FormParam("email") String email,
            @FormParam("password") String password,
            @Context HttpServletRequest request) throws NoSuchAlgorithmException, UnsupportedEncodingException, CloneNotSupportedException {

        UsuarioDAO dao = new UsuarioDAO();
        LogLoginDAO logDAO = new LogLoginDAO();
        /*
        *Captando as informações para o Loglogin
         */
        String ipAdress = request.getRemoteAddr();
        String tipo = request.getHeader("User-Agent");
        Date date = new Date(System.currentTimeMillis());

        LogLogin log = new LogLogin(date, ipAdress, tipo);

        email = new String(Base64.getDecoder().decode(email));
        password = new String(Base64.getDecoder().decode(password));

        password = dao.md5Converte(password);
        return Response
                .ok(dao.login(email, password, log))
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers",
                        "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .build();
    }

}
