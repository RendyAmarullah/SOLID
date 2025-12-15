package com.company;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConection {
    public Connection mySqlConection;
    public Connection connection(){
        if (mySqlConection == null){
            try {
                String DB="jdbc:mysql://localhost:3306/manajemen_perpustakaan?serverTimezone=UTC";
                String user="root"; // user database
                String pass=""; // password database
                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                mySqlConection = (Connection) DriverManager.getConnection(DB,user,pass);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return mySqlConection;
    }
}

