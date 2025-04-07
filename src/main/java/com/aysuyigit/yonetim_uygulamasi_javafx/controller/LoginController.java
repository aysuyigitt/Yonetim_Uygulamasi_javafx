package com.aysuyigit.yonetim_uygulamasi_javafx.controller;
import com.aysuyigit.yonetim_uygulamasi_javafx.dao.UserDAO;
import com.aysuyigit.yonetim_uygulamasi_javafx.dto.UserDTO;
import com.aysuyigit.yonetim_uygulamasi_javafx.utils.SpecialColor;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import  javafx.scene.control.TextField;

import java.util.Optional;

public class LoginController {
    //Injection
    private UserDAO userDAO;

    //Parametresiz construcutor
    public LoginController(){
        userDAO = new UserDAO();
    }

    @FXML
    private  TextField usernameField;

    @FXML
    private  TextField passwordField;

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    //Enter tuşuna basıldığında giriş yap
    @FXML
    private void specialOnEnterPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            //Entera basıldığında login sayfasına giriş
            login();
        }
    }

    @FXML
    public void login(){
        //Kullanıcıdan giriş yaparken username,password almak
        String username,password;
        username = usernameField.getText();
        password = usernameField.getText();

        //UserDTO  verisini almak
        Optional<UserDTO> optionalLoginUserDTO = userDAO.loginUser(username,password);
        //Eğer veri boş değilse
        if(optionalLoginUserDTO.isPresent()){
            UserDTO userDTO = optionalLoginUserDTO.get();//veri alındı
            //Başarılıysa ekranda göster
            showAlert("Başarılı","Giriş başarılı",Alert.AlertType.INFORMATION);
            //Kayıt başarılıysa Admin paneline göster
            openAdminPane();

        }else{
            //Eğer bilgiler yanlışsa
            showAlert("Başarılı","Giriş başarılı",Alert.AlertType.ERROR);

        }

    }

    private void openAdminPane() {
        try {
            //fxml dosyalarını yükle
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource
                    ("/com/aysuyigit/yonetim_uygulamasi_javafx/view/admin.fxml"));
            Parent parent = fxmlLoader.load();

            //Admin sayfasına veri gönderme
            Stage stage = (Stage)usernameField.getScene().getWindow();
            stage.setScene(new Scene(parent));
            //Pencere başlığını Admin Panel olarak ayarla
            stage.setTitle("Admin Panel" +usernameField);
            stage.show();


        } catch (Exception e) {
            System.out.println(SpecialColor.RED + "Admin Sayfasına yönlendirme başarısız" + SpecialColor.RESET);
            e.printStackTrace();
            showAlert("Hata", "Admin ekranı yüklenemedi", Alert.AlertType.ERROR);
        }
    }


    //Sayfalar arası geçiş(LOGİN-REGİSTER)
    @FXML
    private void switchToRegister(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource
                    ("/com/aysuyigit/yonetim_uygulamasi_javafx/view/register.fxml"));
            Parent parent = fxmlLoader.load();

            Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(parent));
            stage.setTitle("Kayıt Ol");
            stage.show();

            // 2.YOL
        } catch (Exception e) {
            System.out.println(SpecialColor.RED + "Register Sayfasına yönlendirme başarısız" + SpecialColor.RESET);
            e.printStackTrace();
            showAlert("Hata", "Kayıt ekranı yüklenemedi", Alert.AlertType.ERROR);
        }
    }
}
