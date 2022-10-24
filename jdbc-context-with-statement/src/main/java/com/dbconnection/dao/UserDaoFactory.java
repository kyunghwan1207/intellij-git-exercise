package com.dbconnection.dao;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

@Configurable
public class UserDaoFactory {
    @Bean
    public UserDao localUserDao(){
        return new UserDao(new LocalConnectionMaker());
    }
}
