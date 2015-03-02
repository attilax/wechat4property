package com.attilax.db;

import java.io.File;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class CreateTable {

 

    private static Session session = null;

    private static Configuration config = null;

    private static Transaction tx = null;

 

    public static void main(String args[]) {

 

        config = new Configuration()

                .configure(new File("src/com/attilax/db/hibernate.cfg.xml"));

        SessionFactory sessionFactory = config.buildSessionFactory();

        session = sessionFactory.openSession();

        tx = session.beginTransaction();

 

        System.out.println("Creating tables...");

        SchemaExport schemaExport = new SchemaExport(config);

        schemaExport.setOutputFile("E:\\sql1.txt");

        schemaExport.create(true, false);

        System.out.println("Table created.");

        tx.commit();

 

    }

 

}