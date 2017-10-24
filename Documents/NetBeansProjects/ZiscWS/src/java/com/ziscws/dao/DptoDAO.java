/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.dao;

import com.google.gson.Gson;
import com.ziscws.entidades.Alerta;
import com.ziscws.entidades.DptoPolicia;
import com.ziscws.hibernate.HibernateUtil;
import com.ziscws.util.JsonFactory;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Avanti Premium
 */
public class DptoDAO {

    private static SessionFactory sessionFactory;

    private Session session;
    private Criteria criteria;
    private final JsonFactory factory = new JsonFactory();
    Gson gson = new Gson();

    public DptoDAO() {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (HibernateException t) {
            throw new ExceptionInInitializerError(t);
        }
    }

    public String buscaDpto() {
        session = sessionFactory.openSession();
        Transaction tx = null;
        String json = null;

        try {
            tx = session.beginTransaction();
            criteria = session.createCriteria(DptoPolicia.class);
            tx.setTimeout(5);
            List<DptoPolicia> lista = criteria.list();
            json = factory.toJsonRestriction(lista, "senha");
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
