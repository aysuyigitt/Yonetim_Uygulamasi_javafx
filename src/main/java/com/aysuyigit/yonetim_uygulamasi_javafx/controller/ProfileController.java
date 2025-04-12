package com.aysuyigit.yonetim_uygulamasi_javafx.controller;

import com.aysuyigit.yonetim_uygulamasi_javafx.dao.UserDAO;
import com.aysuyigit.yonetim_uygulamasi_javafx.dto.UserDTO;
import com.aysuyigit.yonetim_uygulamasi_javafx.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.util.Optional;

public class ProfileController {

    @FXML
    private Label usernameField;
    @FXML
    private Label emailField;
    @FXML
    private Label roleField;

    private UserDAO userDAO = new UserDAO();


    public void setUser(UserDTO user) {
        // Veritabanından güncel halini alalım
        System.out.println("Kullanıcı Bilgileri: " + user);
        //  this.currentUser = user;
        Optional<UserDTO> dbUser = getUserProfile(user.getId());

        if (dbUser.isPresent()) {
            UserDTO fresh = dbUser.get();
            usernameField.setText(fresh.getUsername());
            emailField.setText(fresh.getEmail());
            roleField.setText(fresh.getRole().toString());
        } else {
            usernameField.setText(user.getUsername());
            emailField.setText(user.getEmail());
            roleField.setText(user.getRole().toString());
        }
    }

    public Optional<UserDTO> getUserProfile(int requestedUserId) {
        return userDAO.findById(requestedUserId);
    }
}