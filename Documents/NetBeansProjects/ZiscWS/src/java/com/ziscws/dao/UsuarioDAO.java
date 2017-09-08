/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.dao;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ziscws.entidades.Usuario;
import com.ziscws.hibernate.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Avanti Premium
 */
public class UsuarioDAO {

    private Session session;
    private Criteria criteria;
    private Disjunction disjunction;

    public UsuarioDAO() {

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        disjunction = Restrictions.disjunction();
        session.beginTransaction();
        criteria = session.createCriteria(Usuario.class);

    }

    /**
     * Busca um usuario de acordo com uma dos parametros (email, cpf ou id) e
     * uma String Json com a restrição escolhida, em caso de não haver
     * restrições completar com String null.
     *
     * @param email
     * @param cpf
     * @param id
     * @param restricao
     * @return Json String
     */
    public String buscaUsuario(String email, String cpf, int id, String restricao) {

        Criterion em = Restrictions.eq("email", email);
        Criterion cp = Restrictions.eq("cpf", cpf);
        Criterion i = Restrictions.eq("id", id);
        disjunction.add(em);
        disjunction.add(cp);
        disjunction.add(i);

        criteria.add(disjunction);
        String json = toJsonRestriction((Usuario) criteria.uniqueResult(), restricao);
        session.close();

        return json;

    }

    /**
     * Faz a consulta usando Criteria se existe usuario com os dados passados,
     * usa o restriction para bloquear a passagem de senha no retorno.
     *
     * @param email
     * @param password
     * @return Json tipo Usuario com restricao de senha
     */
    public String login(String email, String password) {

        criteria.add(Restrictions.eq("email", email));
        criteria.add(Restrictions.eq("senha", password));

        String json = toJsonRestriction((Usuario) criteria.uniqueResult(), "senha");

        session.close();
        return json;

    }

    /**
     * Cria String Json com o objeto e com a restricao de campo passado, caso
     * não tenha restrição, passar a String null como parametro
     *
     * @param object
     * @param restriction
     * @return String Json
     */
    public String toJsonRestriction(Object object, String restriction) {

        GsonBuilder builder = new GsonBuilder();
        builder.setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fa) {
                return fa.getName().contains(restriction);
            }

            @Override
            public boolean shouldSkipClass(Class<?> type) {
                return false;
            }
        });

        Gson gson = builder.create();

        return gson.toJson(object);
    }

}
