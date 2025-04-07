package com.aysuyigit.yonetim_uygulamasi_javafx.controller;

import com.aysuyigit.yonetim_uygulamasi_javafx.dao.UserDAO;
import com.aysuyigit.yonetim_uygulamasi_javafx.dto.UserDTO;
import com.aysuyigit.yonetim_uygulamasi_javafx.utils.SpecialColor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.Optional;

public class RegisterController {

    //Injection
    private UserDAO userDAO;

    //Parametresiz construcutor
    public RegisterController(){
        userDAO = new UserDAO();
    }

    @FXML
    private TextField usernameField;

    @FXML
    private  TextField passwordField;

    @FXML
    private  TextField emailField;


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
            register();
        }
    }


    //Register
    @FXML
    public void register(){
        //Kullanıcıdan giriş yaparken username,password almak
        String username,password,email;
        username = usernameField.getText();
        password = usernameField.getText();
        email = emailField.getText();

        Optional<UserDTO> optionalRegisterUserDTO = Optional.ofNullable(UserDTO.builder()
                .id(0)
                .password(password)
                .email(email)
                .username(username)
                .build());


        //Eğer veri boş değilse
        if(optionalRegisterUserDTO.isPresent()){
            UserDTO userDTO = optionalRegisterUserDTO.get();//veri alındı
            //Başarılıysa ekranda göster
            showAlert("Başarılı","Kayıt başarılı",Alert.AlertType.INFORMATION);

            //Kayıt başarılıysa Admin paneline göster
            switchToLoginPane();

        }else {
            //Eğer bilgiler yanlışsa
            showAlert("Başarılı", "Kayıt başarılı", Alert.AlertType.ERROR);

        }
    }
    //Eğer kayıt işilemi başarılıysa login ekranına gönder
        @FXML
        private void switchToLoginPane() {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource
                        ("/com/aysuyigit/yonetim_uygulamasi_javafx/view/login.fxml"));
                Parent parent = fxmlLoader.load();

                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(new Scene(parent));
                stage.setTitle("Giriş Yap");
                stage.show();

                // 2.YOL
            } catch (Exception e) {
                System.out.println(SpecialColor.RED + "Login Sayfasına yönlendirme başarısız" + SpecialColor.RESET);
                e.printStackTrace();
                showAlert("Hata", "Login ekranı yüklenemedi", Alert.AlertType.ERROR);
            }
    }
    }

