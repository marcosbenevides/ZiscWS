/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.dao;

import com.google.gson.Gson;
import com.ziscws.entidades.LogLogin;
import com.ziscws.util.JsonFactory;
import java.util.List;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Marcos Benevides
 */
public class LogLoginDAO {

    private static SessionFactory sessionFactory;

    private Session session;
    private Criteria criteria;
    private final JsonFactory factory = new JsonFactory();
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    Gson gson = new Gson();

    public LogLoginDAO() {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (HibernateException t) {
            throw new ExceptionInInitializerError(t);
        }
    }

    public String getHistorico(Long idUsuario) {

        session = sessionFactory.openSession();
        Transaction tx = null;
        String json = null;

        try {
            tx = session.beginTransaction();
            criteria = session.createCriteria(LogLogin.class);
            tx.setTimeout(5);
            criteria.add(Restrictions.eq("usuario", idUsuario));
            List<LogLogin> log = criteria.list();
            json = factory.toJsonRestriction(log, "senha");
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

    public String getHistoricoIP(String ip) {

        session = sessionFactory.openSession();
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

    public String getHistoricoId(Long idHistorico) {
        session = sessionFactory.openSession();
        Transaction tx = null;
        String json = null;
        try {
            tx = session.beginTransaction();
            criteria = session.createCriteria(LogLogin.class);
            tx.setTimeout(5);
            criteria.add(Restrictions.eq("id", idHistorico));
            LogLogin log = (LogLogin) criteria.uniqueResult();
            json = factory.toJsonRestriction(log, "senha");
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
            //session.close();
        }
        return json;
    }

    public String novoLog(LogLogin log) {
        Transaction tx = null;
        String json = null;
        try {
            if (deslogar(log)) {
                session = sessionFactory.openSession();
                tx = session.beginTransaction();
                criteria = session.createCriteria(LogLogin.class);
                tx.setTimeout(5);

                session.save(log);
            } else {
                return "null";
            }
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
            //session.close();
        }
        return getHistoricoId(log.getId());
    }

    private boolean deslogar(LogLogin log) {
        session = sessionFactory.openSession();
        Transaction tx = null;
        String json = null;
        Integer count = 0;

        try {
            tx = session.beginTransaction();
            criteria = session.createCriteria(LogLogin.class);
            tx.setTimeout(5);

            criteria.add(Restrictions.eq("usuario", log.getUsuario()));
            criteria.add(Restrictions.eq("ativo", 1));

            List<LogLogin> lista = criteria.list();

            if (lista.size() >= 1) {
                for (LogLogin login : lista) {
                    login.setAtivo(2);
                    session.saveOrUpdate(login);
                }
            }
            count = criteria.list().size();
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
        return count == 0;
    }

    public boolean isUUIDValid(String UUID) {

        session = sessionFactory.openSession();
        Transaction tx = null;
        String json = null;
        Integer size = 0;
        try {
            tx = session.beginTransaction();
            criteria = session.createCriteria(LogLogin.class);
            tx.setTimeout(5);

            criteria.add(Restrictions.eq("UUID", UUID));

            size = criteria.list().size();
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

        return size > 0;
    }

}
