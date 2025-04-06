package com.aysuyigit.yonetim_uygulamasi_javafx.dao;

import java.util.Optional;

public interface ILogin <T> {

    Optional<T> loginUser(String username, String password);


}
