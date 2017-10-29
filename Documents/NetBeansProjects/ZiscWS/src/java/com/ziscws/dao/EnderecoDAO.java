/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.dao;

import com.google.gson.Gson;
import com.ziscws.hibernate.HibernateUtil;
import com.ziscws.entidades.Usuario;
import com.ziscws.entidades.Endereco;
import com.ziscws.util.JsonFactory;
import java.util.List;
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
public class EnderecoDAO {

    private static SessionFactory sessionFactory;

    private Session session;
    private Criteria criteria;
    private Disjunction disjunction;
    private JsonFactory factory = new JsonFactory();
    Gson gson = new Gson();

    public EnderecoDAO() {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (HibernateException t) {
            throw new ExceptionInInitializerError(t);
        }
    }

    public String buscaEnderecoUsuario(Usuario usuario) {
        session = sessionFactory.openSession();
        Transaction tx = null;
        String json = null;

        try {
            tx = session.beginTransaction();
            criteria = session.createCriteria(Endereco.class);
            tx.setTimeout(5);
            criteria.add(Restrictions.eq("usuario", usuario));
            List<Endereco> enderecos = criteria.list();
            json = factory.toJsonRestriction(enderecos, "senha");
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

    public String novoEndereco(Endereco endereco, Long idusuario) {
        session = sessionFactory.openSession();
        Transaction tx = null;
        String json = null;
        Usuario usuario = null;

        try {
            tx = session.beginTransaction();
            criteria = session.createCriteria(Endereco.class);
            tx.setTimeout(5);
            usuario = (Usuario) session.load(Usuario.class, idusuario);
            endereco.setUsuario(usuario);
            session.save(endereco);
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
        return buscaEnderecoUsuario(usuario);
    }

}
