package com.aysuyigit.yonetim_uygulamasi_javafx.dao;

import com.aysuyigit.yonetim_uygulamasi_javafx.database.SingletonDBConnection;
import com.aysuyigit.yonetim_uygulamasi_javafx.dto.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class UserDAO implements IDaoImplements<UserDTO> {

    //Dependency
    private Connection connection;

    //Parametresiz Constructor
    public UserDAO(){
        this.connection= SingletonDBConnection.getInstance().getConnection();
    }



    @Override
    public Optional<UserDTO> create(UserDTO userDTO) {
        String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userDTO.getUsername());
            preparedStatement.setString(2, userDTO.getPassword());
            preparedStatement.setString(3, userDTO.getEmail());

        }catch(Exception exception){
            exception.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<UserDTO> list() {
        return List.of();
    }

    @Override
    public Optional<UserDTO> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<UserDTO> findById(int id) {
        return Optional.empty();
    }

    @Override
    public Optional<UserDTO> update(int id, UserDTO entity) {
        return Optional.empty();
    }

    @Override
    public Optional<UserDTO> delete(int id) {
        return Optional.empty();
    }
}
