package com.dbconnection.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;

public class LocalUserDao implements ConnectionMaker{

    @Override
    public Connection makeConnection(){
        Map<String, String> env = System.getenv();
        String dbHost = env.get("LOCAL_DB_HOST");
        String dbUser = env.get("LOCAL_DB_USER");
        String dbPassword = env.get("LOCAL_DB_PASSWORD");
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                    dbHost, dbUser, dbPassword
            );
            System.out.println("LocalUserDao.getConnection()");
            System.out.println("Success Connect to MySQL");

        } catch (Exception e){
            System.out.println("LocalUserDao.getConnection()/exception = " + e);
        }
        return conn;
    }

    public LocalUserDao() {
    }
}
