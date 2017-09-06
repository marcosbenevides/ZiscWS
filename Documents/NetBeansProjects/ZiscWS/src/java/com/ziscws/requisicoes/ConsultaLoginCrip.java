/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.requisicoes;

import com.google.gson.Gson;
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
public class ConsultaLoginCrip {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String loginCrip(@FormParam("email") String email,
            @FormParam("password") String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        System.err.println(email + " " + password);

        email = new String(Base64.getDecoder().decode(email));
        password = new String(Base64.getDecoder().decode(password));
        System.err.println("Decoded: " + email + " " + password);

        MessageDigest digest = MessageDigest.getInstance("MD5");
        byte[] messageDigest = digest.digest(password.getBytes("UTF-8"));
        
        StringBuilder hex = new StringBuilder();
        for (byte b : messageDigest) {
            hex.append(String.format("%02x", b));
        }
        password = new String(hex);

        Consultas con = new Consultas();
        Gson gson = new Gson();
//        Usuario usuario = new Usuario(
//                con.requisicaoLogin(email, password).getIdusuario(),
//                con.requisicaoLogin(email, password).getNome(),
//                con.requisicaoLogin(email, password).getEmail());
        String json = gson.toJson(con.requisicaoLogin(email, password));
        return json;
    }

}
