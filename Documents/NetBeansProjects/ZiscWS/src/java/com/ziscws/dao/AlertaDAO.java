/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.dao;

import com.google.gson.Gson;
import com.ziscws.entidades.Alerta;
import com.ziscws.entidades.Usuario;
import com.ziscws.util.JsonFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

/**
 * Classe que implementa todos os metodos de gravação e consulta de um Alerta
 *
 * @author Avanti Premium
 */
public class AlertaDAO {

    private static SessionFactory sessionFactory;
    private Session session;
    private Criteria criteria;
    private final JsonFactory factory = new JsonFactory();
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    Gson gson = new Gson();

    public AlertaDAO() {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (HibernateException t) {
            throw new ExceptionInInitializerError(t);
        }

    }

    /**
     * Cria alerta com base nos dados enviados
     *
     * @param alerta
     * @param usuario
     * @return Json do alerta
     */
    public String novoAlerta(Alerta alerta, Long usuario) {
        session = sessionFactory.openSession();
        Transaction tx = null;
        String json = null;
        try {
            tx = session.beginTransaction();
            criteria = session.createCriteria(Alerta.class);
            tx.setTimeout(5);
            Usuario user = (Usuario) session.load(Usuario.class, usuario);
            alerta.setUsuario(user);
            session.saveOrUpdate(alerta);
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
        json = buscaAlertaID(alerta.getId());

        return json;
    }

    /**
     * Busca Alertas de acordo com a posição que é enviado como parametro,
     *
     * @param longitude
     * @param latitude
     * @return Lista de Alertas que estão dentro de um raio de 2KM
     */
    public String buscaPorLocal(String longitude, String latitude) {
        session = sessionFactory.openSession();
        Transaction tx = null;
        String json = null;

        try {
            tx = session.beginTransaction();
            criteria = session.createCriteria(Alerta.class);
            tx.setTimeout(5);

            List<Alerta> listaCompleta = (List<Alerta>) criteria.list();
            List<Alerta> listaNova = new ArrayList<>();

            for (int i = 0; i < listaCompleta.size(); i++) {
                if (distancia2Pontos(listaCompleta.get(i).getLatitude(),
                        listaCompleta.get(i).getLongitude(),
                        latitude,
                        longitude)) {
                    listaNova.add(listaCompleta.get(i));
                }
            }
            if (listaNova.size() == 0) {
                json = "null";
            } else {
                json = factory.toJsonRestriction(listaNova, "senha");
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
            session.close();
        }

        return json;
    }

    /**
     * Busca alerta com base no id
     *
     * @param id
     * @return String Json
     */
    public String buscaAlertaID(Long id) {
        session = sessionFactory.openSession();
        Transaction tx = null;
        String json = null;

        try {
            tx = session.beginTransaction();
            criteria = session.createCriteria(Alerta.class);
            tx.setTimeout(5);
            criteria.add(Restrictions.eq("id", id));
            Alerta alerta = (Alerta) criteria.uniqueResult();
            json = factory.toJsonRestriction(alerta, "senha");

            if (json.contains("null")) {
                LOGGER.log(Level.INFO, "Alerta do id {0} n\u00e3o foi encontrado.", id);
            } else {
                LOGGER.log(Level.INFO, "Alerta do id {0} encontrado.", id);
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
            session.close();
        }
        return json;
    }

    /**
     * Busca alertas com base no usuario
     *
     * @param usuario
     * @return String Json com os Alertas
     */
    public String buscaAlertaUsuario(Usuario usuario) {
        session = sessionFactory.openSession();
        Transaction tx = null;
        String json = null;

        try {
            tx = session.beginTransaction();
            criteria = session.createCriteria(Alerta.class);
            tx.setTimeout(5);
            criteria.add(Restrictions.eq("usuario", usuario));
            List<Alerta> alertas = criteria.list();
            json = factory.toJsonRestriction(alertas, "senha");
            if (json.contains(null)) {
                LOGGER.log(Level.INFO, "Alertas do usuario -> {0} n\u00e3o foram encontrados!", usuario.getId());
            } else {
                LOGGER.log(Level.INFO, "Foram encontrados -> {0} alertas para o usuario -> {1}", new Object[]{alertas.size(), usuario.getId()});
            }
            tx.commit();

        } catch (HibernateException ex) {
            try {
                if (tx != null) {
                    tx.rollback();
                }
                ex.printStackTrace();

                json = gson.toJson(ex);
            } catch (RuntimeException e) {
                json += gson.toJson(e);
            }
        } finally {
            session.close();
        }
        return json;
    }

    /**
     * Função para calcular a distancia entre dois pontos e verificar se ambos
     * estão entre uma distancia de 2000 metros.
     *
     * @param latA
     * @param longA
     * @param latB
     * @param longB
     * @return True caso os pontos estão em uma distancia menor que 2 KM
     */
    public Boolean distancia2Pontos(String latA, String longA, String latB, String longB) {

        double earthRadius = 6371;//kilometers
        double dLat = Math.toRadians(Double.parseDouble(latB) - Double.parseDouble(latA));
        double dLng = Math.toRadians(Double.parseDouble(longB) - Double.parseDouble(longA));
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(Double.parseDouble(latA)))
                * Math.cos(Math.toRadians(Double.parseDouble(latB)));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = (earthRadius * c) * 1000;

        return dist <= 2000;
    }

    public String todosAlertas() {
        session = sessionFactory.openSession();
        Transaction tx = null;
        String json = null;

        try {
            tx = session.beginTransaction();
            tx.setTimeout(5);
            criteria = session.createCriteria(Alerta.class);

            List<Alerta> alerta = criteria.list();
            json = factory.toJsonRestriction(alerta, "senha");

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
}
