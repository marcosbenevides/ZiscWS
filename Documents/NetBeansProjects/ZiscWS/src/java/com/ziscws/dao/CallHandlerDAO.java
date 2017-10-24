/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.dao;

import com.google.gson.Gson;
import com.ziscws.entidades.CallHandler;
import com.ziscws.hibernate.HibernateUtil;
import com.ziscws.util.JsonFactory;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

/**
 * Classe responsavel por gerir as funções da classe CallHandler
 *
 * @author Avanti Premium
 */
public class CallHandlerDAO {

    private static SessionFactory sessionFactory;

    private Session session;
    private Criteria criteria;
    private final JsonFactory factory = new JsonFactory();
    Gson gson = new Gson();

    public CallHandlerDAO() {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (HibernateException t) {
            throw new ExceptionInInitializerError(t);
        }
    }

    /**
     * Cria um novo CallHandler no banco de dados
     *
     * @param callHandler
     * @return call criado.
     */
    public String setCallHandler(CallHandler callHandler) {
        session = sessionFactory.openSession();
        Transaction tx = null;
        String json = null;

        try {
            tx = session.beginTransaction();
            criteria = session.createCriteria(CallHandler.class);
            tx.setTimeout(5);
            session.saveOrUpdate(callHandler);
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

        return factory.toJsonRestriction(callHandler, "senha");
    }

    /**
     * Busca todas as calls com status ativo
     *
     * @return lista de calls
     */
    public String getCall() {
        session = sessionFactory.openSession();
        Transaction tx = null;
        String json = null;

        try {
            tx = session.beginTransaction();
            criteria = session.createCriteria(CallHandler.class);
            tx.setTimeout(5);
            criteria.add(Restrictions.eq("ativo", true));
            List<CallHandler> list = criteria.list();

            json = factory.toJsonRestriction(list, "senha");
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
     * Busca call de acordo com o parametro passado.
     *
     * @param id
     * @return call
     */
    public String getCall(Long id) {

        session = sessionFactory.openSession();
        Transaction tx = null;
        String json = null;
        Gson gson = new Gson();

        try {
            tx = session.beginTransaction();
            criteria = session.createCriteria(CallHandler.class);
            tx.setTimeout(5);
            criteria.add(Restrictions.eq("id", id));
            CallHandler call = (CallHandler) criteria.uniqueResult();
            json = gson.toJson(call);
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

}
