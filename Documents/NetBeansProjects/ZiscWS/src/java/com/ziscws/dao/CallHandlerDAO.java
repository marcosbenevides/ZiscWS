/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.dao;

import com.google.gson.Gson;
import com.ziscws.entidades.CallHandler;
import com.ziscws.entidades.DptoPolicia;
import com.ziscws.hibernate.HibernateUtil;
import com.ziscws.util.JsonFactory;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Avanti Premium
 */
public class CallHandlerDAO {

    private Session session;
    private Criteria criteria;
    private final JsonFactory factory = new JsonFactory();

    public void beginTransaction() {

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        criteria = session.createCriteria(CallHandler.class);

    }

    public String setCallHandler(CallHandler callHandler) {
        beginTransaction();
        session.saveOrUpdate(callHandler);
        session.getTransaction().commit();

        return factory.toJsonRestriction(callHandler, "senha");
    }

    public String getCall() {
        beginTransaction();
        criteria.add(Restrictions.eq("ativo", true));
        List<CallHandler> list = criteria.list();

        String json = factory.toJsonRestriction(list, "senha");
        session.close();
        return json;
    }

    public String getCall(Long id) {
        Gson gson = new Gson();
        beginTransaction();
        criteria.add(Restrictions.eq("id", id));
        CallHandler call = (CallHandler) criteria.uniqueResult();
        String json = gson.toJson(call);
        session.close();
        return json;
    }

}
