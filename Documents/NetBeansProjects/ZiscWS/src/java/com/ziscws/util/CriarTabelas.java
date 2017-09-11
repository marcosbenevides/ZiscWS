/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.util;

import com.ziscws.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 *
 * @author Avanti Premium
 */
public class CriarTabelas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Configuration config = new Configuration();
        config.configure();
        new SchemaExport(config).create(true, true);
//        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//        Configuration configuration = new Configuration();
//        configuration = configuration.configure();
//        session = (Session) configuration.buildSessionFactory();

    }

}
