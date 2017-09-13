/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author Avanti Premium
 */
public class JsonFactory {
    
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
