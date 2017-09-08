/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.requisicoes;

import com.google.gson.Gson;
import com.ziscws.entidades.Usuario;
import com.ziscws.hibernate.HibernateUtil;
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
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

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
            @FormParam("password") String password) throws NoSuchAlgorithmException, UnsupportedEncodingException, CloneNotSupportedException {

        email = new String(Base64.getDecoder().decode(email));
        password = new String(Base64.getDecoder().decode(password));

        MessageDigest digest = MessageDigest.getInstance("MD5");
        byte[] messageDigest = digest.digest(password.getBytes("UTF-8"));

        StringBuilder hex = new StringBuilder();
        for (byte b : messageDigest) {
            hex.append(String.format("%02x", b));
        }
        password = new String(hex);

        Gson gson = new Gson();
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        Criteria criteria = session.createCriteria(Usuario.class);
        criteria.add(Restrictions.eq("email", email));
        criteria.add(Restrictions.eq("senha", password));
        
        String json = gson.toJson((Usuario) criteria.uniqueResult());
        session.close();
        return json;
    }

}
