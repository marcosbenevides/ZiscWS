/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.dao;

import com.ziscws.hibernate.HibernateUtil;
import com.ziscws.entidades.Usuario;
import com.ziscws.entidades.Endereco;
import com.ziscws.util.JsonFactory;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Avanti Premium
 */
public class EnderecoDAO {

    private Session session;
    private Criteria criteria;
    private Disjunction disjunction;
    private JsonFactory factory = new JsonFactory();

    public void beginTransaction() {

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        disjunction = Restrictions.disjunction();
        session.beginTransaction();
        criteria = session.createCriteria(Endereco.class);

    }
    
    public String buscaEnderecoUsuario(Usuario usuario){
        
        beginTransaction();
        criteria.add(Restrictions.eq("usuario", usuario));
        List<Endereco> enderecos  =  criteria.list();
        String json = factory.toJsonRestriction(enderecos, "senha");
        session.close();
        return json;
    }
    
    public String novoEndereco(Endereco endereco, Long idusuario){
        
        beginTransaction();
        Usuario usuario = (Usuario)session.load(Usuario.class, idusuario);
        endereco.setUsuario(usuario);
        session.save(endereco);
        session.getTransaction().commit();
        
        return buscaEnderecoUsuario(usuario);
    }

}
