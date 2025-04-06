package com.aysuyigit.yonetim_uygulamasi_javafx.dao;


import com.aysuyigit.yonetim_uygulamasi_javafx.database.SingletonDBConnection;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;


public interface IDaoImplements <T> {

    //CREATE
    Optional<T> create(T t);

    //List
    Optional<List<T>>list();

    //FIND
    Optional<T> findByName(String name);

    Optional<T> findById(int id);

    //UPDATE
    Optional<T> update(int id, T t);

    //DELETE
    Optional<T> delete(int id);


    default Connection iDaoImplementsDatabaseConnection() {
         return SingletonDBConnection.getInstance().getConnection();
    }

}

