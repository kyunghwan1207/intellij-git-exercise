package com.dbconnection.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionMaker {
    public abstract Connection makeConnection();
}
