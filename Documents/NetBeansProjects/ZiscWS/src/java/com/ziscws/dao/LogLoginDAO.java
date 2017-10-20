/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.dao;

import com.ziscws.entidades.LogLogin;
import com.ziscws.hibernate.HibernateUtil;
import com.ziscws.util.JsonFactory;
import java.util.List;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Marcos Benevides
 */
public class LogLoginDAO {

    private Session session;
    private Criteria criteria;
    private final JsonFactory factory = new JsonFactory();
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public String getHistorico(Long id) {

        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        String json = null;

        try {
            tx = session.beginTransaction();
            criteria = session.createCriteria(LogLogin.class);
            tx.setTimeout(5);
            criteria.add(Restrictions.eq("usuario", id));
            List<LogLogin> log = criteria.list();
            json = factory.toJsonRestriction(log, "senha");
            tx.commit();
        } catch (HibernateException ex) {
            try {
                tx.rollback();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        } finally {
            session.close();
        }
        return json;
    }

    public String getHistoricoIP(String ip) {

        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        String json = null;

        try {
            tx = session.beginTransaction();
            criteria = session.createCriteria(LogLogin.class);
            tx.setTimeout(5);
            criteria.add(Restrictions.eq("ip", ip));
            List<LogLogin> log = criteria.list();
            json = factory.toJsonRestriction(log, "senha");
            tx.commit();
        } catch (HibernateException ex) {
            try {
                tx.rollback();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        } finally {
            session.close();
        }

        return json;
    }

    public void novoLog(LogLogin log) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        String json = null;

        try {
            tx = session.beginTransaction();
            criteria = session.createCriteria(LogLogin.class);
            tx.setTimeout(5);
            session.saveOrUpdate(log);
            tx.commit();
        } catch (HibernateException ex) {
            try {
                tx.rollback();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        } finally {
            session.close();
        }
    }
}
