/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.dao;

import com.google.gson.Gson;
import com.ziscws.entidades.LogLogin;
import com.ziscws.entidades.Usuario;
import com.ziscws.hibernate.HibernateUtil;
import com.ziscws.util.JsonFactory;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Avanti Premium
 */
public class UsuarioDAO {

    private static SessionFactory sessionFactory;

    private Gson gson = new Gson();
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
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (HibernateException t) {
            throw new ExceptionInInitializerError(t);
        }

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

        session = sessionFactory.openSession();
        Transaction tx = null;
        String json = null;

        try {
            tx = session.beginTransaction();
            criteria = session.createCriteria(Usuario.class);
            tx.setTimeout(5);
            criteria.add(Restrictions.eq("email", email));
            json = factory.toJsonRestriction(criteria.uniqueResult(), restricao);
            tx.commit();
        } catch (HibernateException ex) {
            try {
                if (tx != null) {
                    tx.rollback();
                }
                ex.printStackTrace();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        } finally {
            session.close();
        }

        return json;

    }

    /**
     * Busca usuario com base no ID
     *
     * @param id
     * @return Usuario
     */
    public Usuario buscaUsuario(Long id) {

        session = sessionFactory.openSession();
        Transaction tx = null;
        String json = null;
        Usuario usuario = null;
        try {
            tx = session.beginTransaction();
            criteria = session.createCriteria(Usuario.class);
            tx.setTimeout(5);

            criteria.add(Restrictions.eq("id", id));
            usuario = new Usuario((Usuario) criteria.uniqueResult());
            tx.commit();
        } catch (HibernateException ex) {
            try {
                if (tx != null) {
                    tx.rollback();
                }
                ex.printStackTrace();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        } finally {
            session.close();
        }

        return usuario;

    }

    /**
     * Faz a consulta usando Criteria se existe usuario com os dados passados,
     * usa o restriction para bloquear a passagem de senha no retorno. Tudo
     * salvo em logs.
     *
     * @param email
     * @param password
     * @param log
     * @return Json tipo Usuario com restricao de senha
     */
    public String login(String email, String password, LogLogin log) {

        session = sessionFactory.openSession();
        Transaction tx = null;
        String json = null;

        try {
            tx = session.beginTransaction();
            criteria = session.createCriteria(Usuario.class);
            tx.setTimeout(5);
            LogLoginDAO logDAO = new LogLoginDAO();
            criteria.add(Restrictions.eq("email", email));
            criteria.add(Restrictions.eq("senha", password));

            json = factory.toJsonRestriction((Usuario) criteria.uniqueResult(), "senha");
            if (json.contains("null")) {
                LOGGER.info("Loggin não efetuado!");
                tx.commit();
            } else {
                LOGGER.info("Loggin efetuado com sucesso!");
                log.setUsuario((Usuario) criteria.uniqueResult());
                tx.commit();
                logDAO.novoLog(log);
            }

        } catch (HibernateException ex) {
            try {
                if (tx != null) {
                    tx.rollback();
                }
                ex.printStackTrace();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        } finally {
            session.close();
        }

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

        session = sessionFactory.openSession();
        Transaction tx = null;
        List list = null;
        try {
            tx = session.beginTransaction();
            criteria = session.createCriteria(Usuario.class);
            tx.setTimeout(5);
            criteria.add(Restrictions.eq("email", email));
            list = criteria.list();
            tx.commit();
        } catch (HibernateException ex) {
            try {
                if (tx != null) {
                    tx.rollback();
                }
                ex.printStackTrace();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        } finally {
            session.close();
        }

        return !list.isEmpty();
    }

    /**
     * Cria usuario no banco de dados
     *
     * @param usuario
     * @return usuario cadastrado.
     */
    public String novoUsuario(Usuario usuario) {

        session = sessionFactory.openSession();
        Transaction tx = null;
        String json = null;

        try {
            tx = session.beginTransaction();
            tx.setTimeout(5);
            session.saveOrUpdate(usuario);
            tx.commit();
        } catch (HibernateException ex) {
            try {
                if (tx != null) {
                    tx.rollback();
                }
                ex.printStackTrace();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        } finally {
            session.close();
        }

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
