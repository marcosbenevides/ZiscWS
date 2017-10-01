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
import org.hibernate.Session;
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

    public void beginTransaction() {

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        criteria = session.createCriteria(LogLogin.class);

    }

    public String getHistorico(Long id) {

        beginTransaction();
        criteria.add(Restrictions.eq("usuario", id));
        List<LogLogin> log = criteria.list();
        String json = factory.toJsonRestriction(log, null);
        session.close();
        return json;
    }

    public String getHistoricoIP(String ip) {
        beginTransaction();
        criteria.add(Restrictions.eq("ip", ip));
        List<LogLogin> log = criteria.list();
        String json = factory.toJsonRestriction(log, null);
        session.close();
        return json;
    }
    
    public void novoLog(LogLogin log){
        beginTransaction();
        session.saveOrUpdate(log);
        session.getTransaction().commit();
    }
}
