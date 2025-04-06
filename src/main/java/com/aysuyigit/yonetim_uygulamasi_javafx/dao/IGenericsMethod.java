package com.aysuyigit.yonetim_uygulamasi_javafx.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public interface IGenericsMethod<T> {

    T mapToObjectDTO(ResultSet resultSet) throws SQLException;

    public Optional<T> selectSingle(String sql, Object...params);

}
