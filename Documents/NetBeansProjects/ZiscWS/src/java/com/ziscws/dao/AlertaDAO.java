/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.dao;

import com.ziscws.entidades.Alerta;
import com.ziscws.entidades.Usuario;
import com.ziscws.hibernate.HibernateUtil;
import com.ziscws.logger.MyLogger;
import com.ziscws.util.JsonFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private final JsonFactory factory = new JsonFactory();
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    public AlertaDAO() {
        try {
            MyLogger.setup();
        } catch (IOException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        LOGGER.setLevel(Level.INFO);
        LOGGER.log(Level.INFO, "Gravando novo usuario!{0}", usuario);
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
            LOGGER.log(Level.INFO, "Alerta criado -> {0}", new Date());
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
        LOGGER.log(Level.INFO, "Buscando alerta pr\u00f3ximos \u00e0: Lat-> {0}Lgn-> {1}",
                new Object[]{latitude, longitude});
        
        for (int i = 0; i < listaCompleta.size(); i++) {
            if (distancia2Pontos(listaCompleta.get(i).getLatitude(),
                    listaCompleta.get(i).getLongitude(),
                    latitude,
                    longitude)) {
                listaNova.add(listaCompleta.get(i));
            }
        }
        LOGGER.log(Level.INFO, "Encontrado: {0} alertas pr\u00f3ximos", listaNova.size());
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
        LOGGER.log(Level.INFO, "Iniciando buscaAlertaID ->{0}", id);
        criteria.add(Restrictions.eq("id", id));
        String json = factory.toJsonRestriction(criteria.uniqueResult(), "senha");
        session.close();
        
        if (json.contains(null)) {
            LOGGER.log(Level.INFO, "Alerta do id ->{0} n\u00e3o encontrado!", id);
        } else {
            LOGGER.log(Level.INFO, "Alerta do id {0} encontrado.", id);
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
        LOGGER.log(Level.INFO, "Iniciado buscaAlertaUsuario -> {0}", usuario.getId());
        criteria.add(Restrictions.eq("usuario", usuario));
        List<Alerta> alertas = criteria.list();
        String json = factory.toJsonRestriction(alertas, "senha");
        if (json.contains(null)) {
            LOGGER.log(Level.INFO, "Alertas do usuario -> {0} n\u00e3o foram encontrados!", usuario.getId());
        } else {
            LOGGER.log(Level.INFO, "Foram encontrados {0} alertas para o usuario -> {1}", new Object[]{alertas.size(), usuario.getId()});
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
        LOGGER.log(Level.INFO, "{0}-->> Foram encontrados -> {1} alertas.", new Object[]{AlertaDAO.class.getName(), alerta.size()});
        session.close();
        return json;
    }
}
