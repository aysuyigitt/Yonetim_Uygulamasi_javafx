package com.aysuyigit.yonetim_uygulamasi_javafx.controller;

import com.aysuyigit.yonetim_uygulamasi_javafx.dao.UserDAO;
import com.aysuyigit.yonetim_uygulamasi_javafx.dto.UserDTO;
import javafx.fxml.FXML;
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

    public void setUser(UserDTO userDTO) {
        if (userDTO == null) {
            System.out.println("UserDTO is null");
            return;
        }

        System.out.println("Profil için gelen kullanıcı: " + userDTO.getUsername());

        Optional<UserDTO> optionalUser = userDAO.findById(userDTO.getId());

        if (optionalUser.isPresent()) {
            UserDTO fresh = optionalUser.get();
            usernameField.setText(fresh.getUsername());
            emailField.setText(fresh.getEmail());
            roleField.setText(fresh.getRole().toString());
        } else {
            usernameField.setText(userDTO.getUsername());
            emailField.setText(userDTO.getEmail());
            roleField.setText(userDTO.getRole().toString());
        }
    }
}