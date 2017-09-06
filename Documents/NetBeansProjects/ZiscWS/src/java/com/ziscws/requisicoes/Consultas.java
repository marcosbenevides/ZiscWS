/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.requisicoes;

import com.ziscws.entidades.*;
import com.ziscws.hibernate.HibernateUtil;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author mikef
 */
public class Consultas {

    @SuppressWarnings("unchecked")
    public Usuario buscaUsuario(String email) {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        s.beginTransaction();

        Query q = s.createQuery("from Usuario u where u.email = :email");
        q.setParameter("email", email);
        List<Usuario> lista = (List<Usuario>) q.list();
        System.err.println("Acessando banco!");
        try {
            Usuario usuario = lista.get(0);
            s.getTransaction().commit();
            System.err.println("Commit!");
            return usuario;
        } catch (IndexOutOfBoundsException e) {
            s.getTransaction().commit();
            Usuario u = null;
            return u;
        }
    }

    @SuppressWarnings("unchecked")
    public Boolean requisicaoLogin(String email, String senha) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        Usuario user = new Usuario();

        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        s.beginTransaction();

        Query q = s.createQuery("from Usuario user where user.email = :email");
//        Query q = s.createSQLQuery("SELECT U.nome AS [Nome], U.email AS [Email] , S.\"hash\" AS [Password]\n"
//                + "FROM SEGURANCA AS S INNER JOIN USUARIO AS U ON S.idusuario = U.idusuario\n"
//                + " WHERE U.email like :email");
        q.setParameter("email", email);

        List<Usuario> lista = (List<Usuario>) q.list();
        char senha1[] = senha.toCharArray();
        
        if (Arrays.equals(lista.get(0).getSenha(), senha1)) {
            s.getTransaction().commit();
            return true;
        }
        s.getTransaction().commit();
        return false;
    }

    @SuppressWarnings("unchecked")
    public List<Alerta> buscaAlerta(String latitude, String longitude) {

        List<Alerta> listaGeral = conAlertaGeral();
        List<Alerta> listatotal = new ArrayList<>();

        for (int i = 0; i < listaGeral.size(); i++) {
            if (distancia2Pontos(listaGeral.get(i).getLatitude(),
                    listaGeral.get(i).getLongitude(),
                    latitude,
                    longitude)) {
                listatotal.add(listaGeral.get(i));
            }
        }

        List<Alerta> lista1 = new ArrayList<>();
        System.err.println("lista do tamanho " + listatotal.size());
        for (int i = 0; i < listatotal.size(); i++) {
            Alerta alerta = listatotal.get(i);
            Alerta alerta1 = new Alerta();
            lista1.add(alerta1);
        }
        return lista1;
    }

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

    public List<Alerta> conAlertaGeral() {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        s.beginTransaction();
        Query q = s.createQuery("from Alerta");
        List<Alerta> lista = (List<Alerta>) q.list();
        s.getTransaction().commit();
        return lista;
    }

    @SuppressWarnings("unchecked")
    public String usuarioValido() {

        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        s.beginTransaction();
        String resultado = "false";

        List<Usuario> lista = (List<Usuario>) s.createQuery("from Usuario u where u.email ='mariaap@gmail.com'").list();
        s.getTransaction().commit();

        if (!lista.isEmpty()) {
            resultado = "true";
        }

        return resultado;
    }

}
