/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.dao;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ziscws.entidades.Usuario;
import com.ziscws.hibernate.HibernateUtil;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Avanti Premium
 */
public class UsuarioDAO {

    private Session session;
    private Criteria criteria;
    private Disjunction disjunction;

    public void beginTransaction() {

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        disjunction = Restrictions.disjunction();
        session.beginTransaction();
        criteria = session.createCriteria(Usuario.class);

    }

    /**
     * Busca um usuario de acordo com uma dos parametros (email, cpf ou id) e
     * uma String Json com a restrição escolhida, em caso de não haver
     * restrições completar com String null.
     *
     * @param email
     * @param cpf
     * @param id
     * @param restricao
     * @return Json String
     */
    public String buscaUsuario(String email, String restricao) {

        beginTransaction();
        criteria.add(Restrictions.eq("email", email));
        String json = toJsonRestriction((Usuario) criteria.uniqueResult(), restricao);
        session.close();

        return json;

    }

    /**
     * Faz a consulta usando Criteria se existe usuario com os dados passados,
     * usa o restriction para bloquear a passagem de senha no retorno.
     *
     * @param email
     * @param password
     * @return Json tipo Usuario com restricao de senha
     */
    public String login(String email, String password) {

        beginTransaction();
        criteria.add(Restrictions.eq("email", email));
        criteria.add(Restrictions.eq("senha", password));

        String json = toJsonRestriction((Usuario) criteria.uniqueResult(), "senha");

        session.close();
        return json;

    }

    /**
     * Cria String Json com o objeto e com a restricao de campo passado, caso
     * não tenha restrição, passar a String null como parametro
     *
     * @param object
     * @param restriction
     * @return String Json
     */
    public String toJsonRestriction(Object object, String restriction) {

        GsonBuilder builder = new GsonBuilder();
        builder.setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fa) {
                return fa.getName().contains(restriction);
            }

            @Override
            public boolean shouldSkipClass(Class<?> type) {
                return false;
            }
        });

        Gson gson = builder.create();

        return gson.toJson(object);
    }

    /**
     * Verifica se usuario está cadastrado
     *
     * @param email
     * @return boolean onde true = usuario cadastrado e false = usuario nao
     * cadastrado
     */
    public boolean usuarioCadastrado(String email) {

        beginTransaction();
        criteria.add(Restrictions.eq("email", email));
        Usuario usuario = (Usuario) criteria.uniqueResult();
        session.close();
        return usuario != null;
    }

    /**
     * Cria usuario no banco de dados
     *
     * @param usuario
     * @return usuario cadastrado.
     */
    public String cadastrarUsuario(Usuario usuario) {
        beginTransaction();

        Gson gson = new Gson();
        session.save(usuario);
        session.getTransaction().commit();

        String json = buscaUsuario(usuario.getEmail(), "senha");
        return json;
    }

    /**
     * Converte a senha do usuario em uma String criptografada
     * @param password
     * @return String md5
     * @throws UnsupportedEncodingException
     */
    public String md5Converte(String password) throws UnsupportedEncodingException {
        StringBuilder hex = new StringBuilder();
        String md5;
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            byte[] messageDigest = digest.digest(password.getBytes("UTF-8"));
            for (byte b : messageDigest) {
                hex.append(String.format("%02x", b));
            }
        } catch (NoSuchAlgorithmException ex) {
            return (UsuarioDAO.class.getName()) + " Exception: " + ex;
        }
        return md5 = new String(hex);
    }

}
