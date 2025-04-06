package com.aysuyigit.yonetim_uygulamasi_javafx.dao;

import com.aysuyigit.yonetim_uygulamasi_javafx.database.SingletonDBConnection;
import com.aysuyigit.yonetim_uygulamasi_javafx.dto.UserDTO;
import com.aysuyigit.yonetim_uygulamasi_javafx.utils.SpecialColor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

            int affectedRows= preparedStatement.executeUpdate();

            if(affectedRows>0){
                try(ResultSet generatedKeys= preparedStatement.getGeneratedKeys()){
                    if(generatedKeys.next()){
                        userDTO.setId((generatedKeys.getInt((1))));
                        return Optional.of(userDTO);
                    }
                }catch(SQLException sqlException){
                    sqlException.printStackTrace();

                }
            }
        }catch(Exception exception){
            exception.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<UserDTO>>list() {
        List<UserDTO> userDTOList = new ArrayList<>();
        String sql = "SELECT*FROM users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery(sql);

            while(resultSet.next()){
                userDTOList.add(UserDTO.builder()
                        .id(resultSet.getInt("id"))
                        .username(resultSet.getString("username"))
                        .password(resultSet.getString("password"))
                        .email(resultSet.getString("email"))
                        .build());
            }
            return userDTOList.isEmpty()? Optional.empty() : Optional.of(userDTOList);
    }catch(Exception exception){
            exception.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserDTO> findByName(String name) {
       // String sql = "SELECT*FROM users WHERE username=?";
        String sql = "SELECT*FROM users WHERE email=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1,name);

            ResultSet resultSet = preparedStatement.executeQuery(sql);
            //Veritabanından gelen veri varsa
            if(resultSet.next()){
                UserDTO userDTO = UserDTO.builder()
                        .id(resultSet.getInt("id"))
                        .username(resultSet.getString("username"))
                        .password(resultSet.getString("password"))
                        .email(resultSet.getString("email"))
                        .build();
                return Optional.of(userDTO);
            }
        }catch(Exception exception){
            exception.printStackTrace();
        }
        //Eğer bulunamazsa boş gönder
        return Optional.empty();
    }

    @Override
    public Optional<UserDTO> findById(int id) {
        String sql = "SELECT*FROM users WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1,id);

            ResultSet resultSet = preparedStatement.executeQuery(sql);
            //Veritabanından gelen veri varsa
            if(resultSet.next()){
                UserDTO userDTO = UserDTO.builder()
                        .id(resultSet.getInt("id"))
                        .username(resultSet.getString("username"))
                        .password(resultSet.getString("password"))
                        .email(resultSet.getString("email"))
                        .build();
                return Optional.of(userDTO);
            }
        }catch(Exception exception){
            exception.printStackTrace();
        }
        //Eğer bulunamazsa boş gönder
        System.out.println(SpecialColor.GREEN+ "Aradığınız"+id+ "id bulunamadı");
        return Optional.empty();
    }


    @Override
    public Optional<UserDTO> update(int id, UserDTO userDTO) {
        Optional<UserDTO> userDTOUpdate = findById(id);
        if (userDTOUpdate.isPresent()) {
            String sql = "UPDATE users SET username=?, password=?, email=?  WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, userDTO.getUsername());
                preparedStatement.setString(2, userDTO.getPassword());
                preparedStatement.setString(3, userDTO.getEmail());
                preparedStatement.setInt(4, userDTO.getId());

                int affectedRows = preparedStatement.executeUpdate();

                //Eğer güncelleme başarılıysa
                if (affectedRows > 0) {
                    userDTO.setId(id);
                    return Optional.of((userDTO));
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
            return Optional.empty();
        }

    @Override
    public Optional<UserDTO> delete(int id) {
        Optional<UserDTO> userDTODelete= findById(id);
        if(userDTODelete.isPresent()){
            String sql = "DELETE FROM userS WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt( 1,id);

                int affectedRows= preparedStatement.executeUpdate();

                //Eğer güncelleme başarılıysa
                if(affectedRows>0){
                    return userDTODelete;
                }
            }catch(Exception exception){
                exception.printStackTrace();
            }
        }
        return Optional.empty();
    }
}
