package com.aysuyigit.yonetim_uygulamasi_javafx.controller;


import com.aysuyigit.yonetim_uygulamasi_javafx.dao.UserDAO;
import com.aysuyigit.yonetim_uygulamasi_javafx.dto.UserDTO;
import javafx.fxml.FXML;
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

        Optional<UserDTO> optionalUserDTO = userDAO.loginUser(username,password);
        //Eğer veri boş değilse
        if(optionalUserDTO.isPresent()){
            UserDTO userDTO = optionalUserDTO.get();//veri alındı
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
    }


}
