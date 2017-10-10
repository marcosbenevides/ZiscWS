/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.dao;

import com.ziscws.entidades.Alerta;
import com.ziscws.entidades.Usuario;
import com.ziscws.hibernate.HibernateUtil;
import com.ziscws.util.JsonFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * Classe que implementa todos os metodos de gravação e consulta de um Alerta
 *
 * @author Avanti Premium
 */
public class AlertaDAO {

    private Session session;
    private Criteria criteria;
    private final JsonFactory factory = new JsonFactory();
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public AlertaDAO() {

    }

    public void beginTransaction() {

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        criteria = session.createCriteria(Alerta.class);

    }

    /**
     * Cria alerta com base nos dados enviados
     *
     * @param alerta
     * @param usuario
     * @return Json do alerta
     */
    public String novoAlerta(Alerta alerta, Long usuario) {
        beginTransaction();
        Usuario user = (Usuario) session.load(Usuario.class, usuario);
        alerta.setUsuario(user);
        session.saveOrUpdate(alerta);
        session.getTransaction().commit();

        String json = buscaAlertaID(alerta.getId());
        if (json.contains(null)) {
            LOGGER.setLevel(Level.SEVERE);
            LOGGER.info("Aconteceu algum erro, Alerta não criado! Checar Log do servidor.");
        } else {
            LOGGER.setLevel(Level.INFO);
            LOGGER.info("Alerta criado -> " + new Date());
        }
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

        beginTransaction();
        List<Alerta> listaCompleta = (List<Alerta>) criteria.list();
        List<Alerta> listaNova = new ArrayList<>();

        LOGGER.setLevel(Level.INFO);
        LOGGER.info("Buscando alerta próximos a Lat -> " + latitude + " Lng -> " + longitude);
        for (int i = 0; i < listaCompleta.size(); i++) {
            if (distancia2Pontos(listaCompleta.get(i).getLatitude(),
                    listaCompleta.get(i).getLongitude(),
                    latitude,
                    longitude)) {
                listaNova.add(listaCompleta.get(i));
            }
        }
        LOGGER.info("Encontrado " + listaNova.size() + " alertas próximos.");
        String json = factory.toJsonRestriction(listaNova, "senha");
        session.close();
        return json;
    }

    /**
     * Busca alerta com base no id
     *
     * @param id
     * @return String Json
     */
    public String buscaAlertaID(Long id) {
        beginTransaction();
        LOGGER.setLevel(Level.INFO);
        LOGGER.info("Iniciando buscaAlertaID ->" + id);
        criteria.add(Restrictions.eq("id", id));
        String json = factory.toJsonRestriction(criteria.uniqueResult(), "senha");
        session.close();

        if (json.contains(null)) {
            LOGGER.info("Alerta do id " + id + " não foi encontrado.");
        } else {
            LOGGER.info("Alerta do id " + id + " encontrado.");
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
        beginTransaction();
        LOGGER.setLevel(Level.INFO);
        LOGGER.info("Iniciado buscaAlertaUsuario -> " + usuario.getId());
        criteria.add(Restrictions.eq("usuario", usuario));
        List<Alerta> alertas = criteria.list();
        String json = factory.toJsonRestriction(alertas, "senha");
        if (json.contains(null)) {
            LOGGER.info("Alertas do usuario -> " + usuario.getId() + " não foram encontrados!");
        } else {
            LOGGER.info("Foram encontrados -> " + alertas.size() + " alertas para o usuario -> " + usuario.getId());
        }
        session.close();

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

        beginTransaction();
        List<Alerta> alerta = criteria.list();
        String json = factory.toJsonRestriction(alerta, "senha");
        LOGGER.info(alerta.size() + " alertas foram encontrados todosAlertas");
        session.close();
        return json;
    }
}
