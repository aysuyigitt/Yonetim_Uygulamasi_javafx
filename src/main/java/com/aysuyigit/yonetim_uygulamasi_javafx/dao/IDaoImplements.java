package com.aysuyigit.yonetim_uygulamasi_javafx.dao;


import com.aysuyigit.yonetim_uygulamasi_javafx.database.SingletonDBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


public interface IDaoImplements <T> extends ICrud<T>, IGenericsMethod<T>, ILogin<T> {
    //Gövdeli Method
    default Connection iDaoImplementsDatabaseConnection() {
         return SingletonDBConnection.getInstance().getConnection();
    }

}

