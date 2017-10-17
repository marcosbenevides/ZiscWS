/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.dao;

import com.ziscws.entidades.Alerta;
import com.ziscws.entidades.DptoPolicia;
import com.ziscws.hibernate.HibernateUtil;
import com.ziscws.util.JsonFactory;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;

/**
 *
 * @author Avanti Premium
 */
public class DptoDAO {
    
    private Session session;
    private Criteria criteria;
    private final JsonFactory factory = new JsonFactory();
    
     public void beginTransaction() {

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        criteria = session.createCriteria(DptoPolicia.class);

    }
     
     public String buscaDpto(){
         beginTransaction();
         List<DptoPolicia> lista = criteria.list();
         String json = factory.toJsonRestriction(lista, "senha");
         session.close();
         return json;
     }
    
}
