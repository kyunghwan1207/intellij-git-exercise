package com.dbconnection.dao;

import com.dbconnection.domain.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private ConnectionMaker connectionMaker;
    private DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public UserDao() {
        // UpCasting
        this.connectionMaker = new LocalConnectionMaker();
    }
    public UserDao(ConnectionMaker connectionMaker){
        this.connectionMaker = connectionMaker;
    }

    public void jdbcConextWithStatementStrategy(StatementStrategy stmt){
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = this.connectionMaker.makeConnection();
            ps = stmt.makePreparedStatement(con);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(ps != null){
                try{
                    ps.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if(con != null){
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void add(User user) throws SQLException {
        Connection mysqlCon = this.connectionMaker.makeConnection();
        if(mysqlCon != null){
            PreparedStatement ps = mysqlCon.prepareStatement(
                    "insert into users(id, name, password) values(?, ?, ?)"
            );
            ps.setInt(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());
            ps.executeUpdate(); // select외의 쿼리는 executeQuery() 사용불가능!
            ps.close();
            mysqlCon.close();
        }
    }
    public void deleteAll() {
        Connection mysqlCon = null;
        PreparedStatement ps = null;
        int rs = -1;
        try {
            mysqlCon = this.connectionMaker.makeConnection();
            ps = mysqlCon.prepareStatement("delete from users");
            rs = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (ps != null){
                try{
                    ps.close();
                } catch (SQLException e){
                    throw new RuntimeException(e);
                }
            }
            if (mysqlCon != null){
                try{
                    mysqlCon.close();
                } catch (SQLException e){
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public User findById(int userId){
        Connection mysqlCon = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User findedUser = null;
        try {
            mysqlCon = this.connectionMaker.makeConnection();
            // Query문 작성
            ps = mysqlCon.prepareStatement(
                    "select * from users where users.id=?"
            );
            ps.setInt(1, userId);
            // Query문 실행
            rs = ps.executeQuery();
            if(rs.next()){
                findedUser = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        } finally {
            if (rs != null){
                try{
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (mysqlCon != null){
                try {
                    mysqlCon.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return findedUser;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();
        userDao.add(new User(7, "ko7", "1234"));
        System.out.println("Success Add user");
        User findedUser = userDao.findById(7);
        System.out.println("findedUser.getName() = " + findedUser.getName());
        userDao.deleteAll();
        System.out.println("Success to delete all from users");
    }
}
