package com.aysuyigit.yonetim_uygulamasi_javafx;

import com.aysuyigit.yonetim_uygulamasi_javafx.database.SingletonDBConnection;
import com.aysuyigit.yonetim_uygulamasi_javafx.utils.SpecialColor;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;




public class HelloApplication extends Application {
    //Proje Açıldığında İlk Açılacak Sayfa
    @Override
    public void start(Stage stage) throws IOException {
        // Proje açıldğında database(h2db) çalışşın)
        initializeDatabase();



        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view/hello-view.fxml"));
       /* FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Kullanıcı Yönetimi!");
        stage.setScene(scene);
        stage.show();*/

    //Login ekranı gelsin
    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view/login.fxml"));
    Parent parent = fxmlLoader.load();
    stage.setTitle("Kullanıcı Yönetimi Login Sayfası");
    stage.setScene(new Scene(parent));
    stage.show();
}



    public void initializeDatabase() {
        try {
            Connection connection = SingletonDBConnection.getInstance().getConnection();

           //Bağlantı alındı
            Statement stmt = connection.createStatement();
            // Kullanıcı tablosu
            String createUserTableSQL = """
                        CREATE TABLE IF NOT EXISTS usertable (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            username VARCHAR(50) NOT NULL UNIQUE,
                            password VARCHAR(255) NOT NULL,
                            email VARCHAR(100) NOT NULL UNIQUE,
                            role VARCHAR(50) DEFAULT 'USER'
                        );
                    """;
            stmt.execute(createUserTableSQL);

            // KDV tablosu
            String createKdvTableSQL = """
                        CREATE TABLE IF NOT EXISTS kdv_table (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            amount DOUBLE NOT NULL,
                            kdvRate DOUBLE NOT NULL,
                            kdvAmount DOUBLE NOT NULL,
                            totalAmount DOUBLE NOT NULL,
                            receiptNumber VARCHAR(100) NOT NULL,
                            transactionDate DATE NOT NULL,
                            description VARCHAR(255),
                            exportFormat VARCHAR(50)
                        );
                    """;
            stmt.execute(createKdvTableSQL);

            System.out.println(SpecialColor.CYAN+"Veritabanı başarıyla oluşturuldu ve veri eklendi"+SpecialColor.RESET);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch();
    }
}