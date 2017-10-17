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
 * Classe responsavel por gerir as funções da classe CallHandler
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

    /**
     * Cria um novo CallHandler no banco de dados
     * @param callHandler
     * @return call criado.
     */
    public String setCallHandler(CallHandler callHandler) {
        beginTransaction();
        session.saveOrUpdate(callHandler);
        session.getTransaction().commit();

        return factory.toJsonRestriction(callHandler, "senha");
    }

    /**
     * Busca todas as calls com status ativo
     * @return lista de calls
     */
    public String getCall() {
        beginTransaction();
        criteria.add(Restrictions.eq("ativo", true));
        List<CallHandler> list = criteria.list();

        String json = factory.toJsonRestriction(list, "senha");
        session.close();
        return json;
    }

    /**
     * Busca call de acordo com o parametro passado.
     * @param id
     * @return call
     */
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
