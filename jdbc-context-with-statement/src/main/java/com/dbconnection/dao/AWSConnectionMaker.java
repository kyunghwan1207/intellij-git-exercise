package com.dbconnection.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class AWSConnectionMaker implements ConnectionMaker{

    @Override
    public Connection makeConnection(){
        Connection awsConn = null;
        try{
            Map<String, String> env = System.getenv();
            awsConn = DriverManager.getConnection(
                    env.get("AWS_DB_HOST"), env.get("AWS_DB_USER"), env.get("AWS_DB_PASSWORD")
            );
        } catch (Exception exception){
            System.out.println("AWSConnectionMaker.makeConnection");
            System.out.println("exception = " + exception);
        }
        return awsConn;
    }
}
