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
import org.hibernate.Transaction;

/**
 *
 * @author Avanti Premium
 */
public class DptoDAO {

    private Session session;
    private Criteria criteria;
    private final JsonFactory factory = new JsonFactory();
    Gson gson = new Gson();

    public String buscaDpto() {
        session = HibernateUtil.getSessionFactory().openSession();
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
                json = gson.toJson(ex);
            } catch (RuntimeException e) {
                json += gson.toJson(e);
            }
        } finally {
            session.close();
        }
        return json;
    }

}
