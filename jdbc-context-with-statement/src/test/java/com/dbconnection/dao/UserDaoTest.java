package com.dbconnection.dao;

import com.dbconnection.domain.User;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes= UserDaoFactory.class)
public class UserDaoTest {
    @Autowired
    ApplicationContext context;

    UserDao userDao;

    @Test
    @DisplayName("사용자 추가 및 조회")
    void addAndSelect() throws SQLException, ClassNotFoundException {
        userDao = context.getBean("localUserDao", UserDao.class);
        User user = new User(3, "ko3", "1234");
        userDao.add(user);
        User findedUser = userDao.findById(3);
        Assertions.assertEquals("ko3", findedUser.getName());
    }
}
