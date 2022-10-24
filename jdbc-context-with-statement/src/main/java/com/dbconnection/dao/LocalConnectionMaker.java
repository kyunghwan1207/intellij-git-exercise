package com.dbconnection.dao;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;

public class LocalConnectionMaker implements ConnectionMaker{
    @Override
    public Connection makeConnection() {
        Connection localConn = null;
        try{
            Map<String, String> env = System.getenv();
            localConn = DriverManager.getConnection(
                    env.get("LOCAL_DB_HOST"), env.get("LOCAL_DB_USER"), env.get("LOCAL_DB_PASSWORD")
            );
        } catch (Exception exception){
            System.out.println("LocalConnectionMaker.makeConnection");
            System.out.println("exception = " + exception);
        }
        return localConn;
    }
}
