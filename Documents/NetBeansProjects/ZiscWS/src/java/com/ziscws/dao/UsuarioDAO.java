/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.dao;

import com.ziscws.entidades.Usuario;
import com.ziscws.hibernate.HibernateUtil;
import com.ziscws.util.JsonFactory;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.hibernate.Criteria;
import org.hibernate.Session;
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
    private JsonFactory factory = new JsonFactory();

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
     * @param restricao
     * @return Json String
     */
    public String buscaUsuarioJson(String email, String restricao) {
        beginTransaction();
        criteria.add(Restrictions.eq("email", email));
        String json = factory.toJsonRestriction(criteria.uniqueResult(), restricao);
        session.close();

        return json;

    }

    /**
     * Busca usuario com base no ID
     * @param id
     * @return Usuario
     */
    public Usuario buscaUsuario(Long id) {

        beginTransaction();
        criteria.add(Restrictions.eq("id", id));
        Usuario usuario = new Usuario((Usuario) criteria.uniqueResult());
        session.close();
        return usuario;

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

        String json = factory.toJsonRestriction((Usuario) criteria.uniqueResult(), "senha");

        session.close();
        return json;
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
    public String novoUsuario(Usuario usuario) {
        beginTransaction();

        session.saveOrUpdate(usuario);
        session.getTransaction().commit();

        return buscaUsuarioJson(usuario.getEmail(), "senha");
    }

    /**
     * Converte a senha do usuario em uma String criptografada
     *
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
