/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ziscws.util;

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

    }

}
