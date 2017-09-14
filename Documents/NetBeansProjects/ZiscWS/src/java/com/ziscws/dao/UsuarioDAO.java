/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.dao;

import com.ziscws.entidades.Usuario;
import com.ziscws.hibernate.HibernateUtil;
import com.ziscws.logger.MyLogger;
import com.ziscws.util.JsonFactory;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Construtor inicia o Logger.
     */
    public UsuarioDAO() {
        try {
            MyLogger.setup();
        } catch (IOException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
        LOGGER.log(Level.INFO, "Inicia buscaUsuarioJson: {0}", email);
        criteria.add(Restrictions.eq("email", email));
        String json = factory.toJsonRestriction(criteria.uniqueResult(), restricao);
        if (json.contains(null)) {
            LOGGER.info("Usuario não encontrado");
        } else {
            LOGGER.info("Usuario encontrado");
        }
        session.close();
        return json;

    }

    /**
     * Busca usuario com base no ID
     *
     * @param id
     * @return Usuario
     */
    public Usuario buscaUsuario(Long id) {

        beginTransaction();
        LOGGER.log(Level.INFO, "Iniciando buscaUsuario: {0}", id);
        criteria.add(Restrictions.eq("id", id));
        Usuario usuario = new Usuario((Usuario) criteria.uniqueResult());
        LOGGER.log(Level.INFO, "Resultado encontrado: {0}", usuario.getId());
        session.close();
        return usuario;

    }

    /**
     * Faz a consulta usando Criteria se existe usuario com os dados passados,
     * usa o restriction para bloquear a passagem de senha no retorno. Tudo
     * salvo em logs.
     *
     * @param email
     * @param password
     * @return Json tipo Usuario com restricao de senha
     */
    public String login(String email, String password) {

        beginTransaction();
        LOGGER.setLevel(Level.INFO);
        criteria.add(Restrictions.eq("email", email));
        criteria.add(Restrictions.eq("senha", password));

        LOGGER.info("Login de usuario: " + email);

        String json = factory.toJsonRestriction((Usuario) criteria.uniqueResult(), "senha");
        if (json.contains("null")) {
            LOGGER.info("Loggin não efetuado!");
        } else {
            LOGGER.info("Loggin efetuado com sucesso!");
        }
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
        List list = criteria.list();
        session.close();
        return !list.isEmpty();
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
