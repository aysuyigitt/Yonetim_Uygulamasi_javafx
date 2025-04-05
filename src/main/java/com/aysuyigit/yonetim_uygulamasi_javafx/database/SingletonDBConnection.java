package com.aysuyigit.yonetim_uygulamasi_javafx.database;

import com.aysuyigit.yonetim_uygulamasi_javafx.utils.SpecialColor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class SingletonDBConnection {

    //private static final String URL = "jdbc:h2./h2db/user_managament" + "AUTO_SERVER=TRUE";
    private static final String URL = "jdbc:h2:~/h2db/user_managament";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    private static SingletonDBConnection instance;
    private static Connection connection;

    private SingletonDBConnection() {
        try {
            Class.forName("org.h2.Driver");
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println(SpecialColor.GREEN + "Veritabanı bağlantısı başarılı" + SpecialColor.RESET);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println(SpecialColor.RED + "Veritabanı bağlantısı başarısız" + SpecialColor.RESET);
            throw new RuntimeException("Veritabanı bağlantısı başarısız");
        }
    }

    public static synchronized SingletonDBConnection getInstance(){
        if(instance==null){
            instance= new SingletonDBConnection();
        }
        return instance;
    }

    public Connection getConnection(){
        return connection;
    }

    //Database kapatmak
    public static  void closeConnection(){
        if(instance!=null&&instance.connection!=null){
            try{
                instance.connection.close();
                System.out.println(SpecialColor.RED + "Veritabanı bağlantısı kapatıldı");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}






