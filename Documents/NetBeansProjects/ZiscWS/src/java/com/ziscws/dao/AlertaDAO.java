/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.dao;

import com.ziscws.entidades.Alerta;
import com.ziscws.entidades.Usuario;
import com.ziscws.hibernate.HibernateUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

/**
 * Classe que implementa todos os metodos de gravação e consulta de um Alerta
 *
 * @author Avanti Premium
 */
public class AlertaDAO {

    private Session session;
    private Criteria criteria;
    private Disjunction disjunction;

    public void beginTransaction() {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        disjunction = Restrictions.disjunction();
        session.beginTransaction();
        criteria = session.createCriteria(Alerta.class);

    }

//    public String buscaAlerta(String longitude, String latitude) {
//
//        beginTransaction();
//        List<Alerta> list = criteria.list();
//        session.close();
//    }

}
