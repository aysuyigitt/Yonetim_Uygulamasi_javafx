package com.aysuyigit.yonetim_uygulamasi_javafx.database;

import com.aysuyigit.yonetim_uygulamasi_javafx.utils.SpecialColor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.h2.tools.Server;
import java.util.Properties;
import java.sql.*;


import java.io.FileInputStream;
import java.io.IOException;

import java.util.Properties;
public class SingletonDBConnection {

    private static String URL = "jdbc:h2:./h2db/user_managament";
    //private static final String URL = "jdbc:h2./h2db/user_managament" + "AUTO_SERVER=TRUE";
    //private static final String URL = "jdbc:h2:~/h2db/user_managament";
    private static String USERNAME = "sa";
    private static String PASSWORD = "";

    private static SingletonDBConnection instance;
    private Connection connection;

    private SingletonDBConnection() {
        try {
            loadDatabaseConfig();
            Class.forName("org.h2.Driver");
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println(SpecialColor.GREEN + "Veritabanı bağlantısı başarılı" + SpecialColor.RESET);
            H2DbStarting();
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println(SpecialColor.RED + "Veritabanı bağlantısı başarısız" + SpecialColor.RESET);
            throw new RuntimeException("Veritabanı bağlantısı başarısız");
        }
    }


    public static synchronized SingletonDBConnection getInstance() {
        if (instance == null) {
            instance = new SingletonDBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    //Database kapatmak
    public static void closeConnection() {
        if (instance != null && instance.connection != null) {
            try {
                instance.connection.close();
                System.out.println(SpecialColor.RED + "Veritabanı bağlantısı kapatıldı");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }
    private static void loadDatabaseConfig() {
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            Properties properties = new Properties();
            properties.load(fis);

            URL = properties.getProperty("db.url", "jdbc:h2:./h2db/user_management");
            USERNAME = properties.getProperty("db.username", "sa");
            PASSWORD = properties.getProperty("db.password", "");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("❌ Veritabanı yapılandırması yüklenemedi!");
        }
    }
    // H2DB
    // H2 Web Konsolunu başlatmak için
    private void H2DbStarting() {
        try {
            Server server = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
            System.out.println("H2 Web Console is running at: http://localhost:8082");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Database Test
        //dataSet();

}




