package com.aysuyigit.yonetim_uygulamasi_javafx.controller;

import com.aysuyigit.yonetim_uygulamasi_javafx.dao.ICrud;
import com.aysuyigit.yonetim_uygulamasi_javafx.dao.ILogin;
import com.aysuyigit.yonetim_uygulamasi_javafx.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public class LoginController implements ICrud<UserDTO>, ILogin<UserDTO> {

    //Inject

    @Override
    public Optional<UserDTO> create(UserDTO userDTO) {
        return Optional.empty();
    }

    @Override
    public Optional<List<UserDTO>> list() {
        return Optional.empty();
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
    public Optional<UserDTO> update(int id, UserDTO userDTO) {
        return Optional.empty();
    }

    @Override
    public Optional<UserDTO> delete(int id) {
        return Optional.empty();
    }


    @Override
    public Optional loginUser(String username, String password) {
        return Optional.empty();
    }
}
