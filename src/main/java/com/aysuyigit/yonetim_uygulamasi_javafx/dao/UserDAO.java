package com.aysuyigit.yonetim_uygulamasi_javafx.dao;

import com.aysuyigit.yonetim_uygulamasi_javafx.database.SingletonDBConnection;
import com.aysuyigit.yonetim_uygulamasi_javafx.dto.UserDTO;
import com.aysuyigit.yonetim_uygulamasi_javafx.utils.ERole;
import com.aysuyigit.yonetim_uygulamasi_javafx.utils.SpecialColor;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserDAO implements IDaoImplements<UserDTO> {

    //Dependency

    private Connection connection;

    public UserDAO() {
        this.connection = SingletonDBConnection.getInstance().getConnection();
    }

    @Override
    public Optional<UserDTO> create(UserDTO userDTO) {
        String sql = "INSERT INTO usertable (username, password, email, role) VALUES (?, ?, ?, ?)"; //Bu satırda, kullanıcı bilgilerini (username, password, email, role) veritabanındaki usertable adlı tabloya eklemek için SQL sorgusu hazırlanır.
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            String hashedPassword = BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt());
           //Bu satırlarda, SQL sorgusundaki yer tutucular (?) sırasıyla kullanıcının username, hashedPassword, email, ve role bilgileri ile doldurulur.
            preparedStatement.setString(1, userDTO.getUsername());
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.setString(3, userDTO.getEmail());
            preparedStatement.setString(4, userDTO.getRole().name());
            //executeUpdate() metodu, INSERT sorgusunu çalıştırarak veritabanına veri ekler. affectedRows değişkeni, kaç satırın etkilendiğini (eklenen satır sayısını) döndürür.
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        userDTO.setId(generatedKeys.getInt(1));
                        userDTO.setPassword(hashedPassword);
                        return Optional.of(userDTO);
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<UserDTO>> list() {
        //İlk olarak, veritabanından alınacak kullanıcıları saklayacak bir List (userDTOList) oluşturulur. Bu liste, veritabanından alınan tüm kullanıcı verilerini içerecek.
        List<UserDTO> userDTOList = new ArrayList<>();
        String sql = "SELECT * FROM usertable";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();//executeQuery() metodu, sorguyu çalıştırır ve sorgunun sonucunu bir ResultSet (sonuç kümesi) olarak döndürür. Bu, veritabanından gelen kullanıcı verilerini içerir.
           //resultSet.next() her seferinde bir kullanıcıyı alır. Bu döngü, tüm kullanıcıları tek tek alıp, mapToObjectDTO(resultSet) metoduyla UserDTO nesnelerine dönüştürür ve userDTOList listesine ekler
            while (resultSet.next()) {
                userDTOList.add(mapToObjectDTO(resultSet));
            }
           return userDTOList.isEmpty() ? Optional.empty() : Optional.of(userDTOList);//Eğer userDTOList listesi boş değilse, Optional.of(userDTOList) ile bu liste döndürülür. Eğer liste boşsa, Optional.empty() döndürülür. Burada Optional, dönen değerin null olmasının önüne geçmek için kullanılır.
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserDTO> findByName(String name) {
        String sql = "SELECT * FROM usertable WHERE email=?";
        return selectSingle(sql, name);
    }

    @Override
    public Optional<UserDTO> findById(int id) {
        String sql = "SELECT * FROM usertable WHERE id=?";
        return selectSingle(sql, id);
    }

    @Override
    public Optional<UserDTO> update(int id, UserDTO userDTO) {
        Optional<UserDTO> optionalUpdate = findById(id);
        if (optionalUpdate.isPresent()) {
            String sql = "UPDATE usertable SET username=?, password=?, email=?, role=? WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                String hashedPassword = BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt());
                preparedStatement.setString(1, userDTO.getUsername());
                preparedStatement.setString(2, hashedPassword);
                preparedStatement.setString(3, userDTO.getEmail());
                preparedStatement.setString(4, userDTO.getRole().name());
                preparedStatement.setInt(5, id);

                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {
                    userDTO.setId(id);
                    userDTO.setPassword(hashedPassword);
                    return Optional.of(userDTO);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserDTO> delete(int id) {
        Optional<UserDTO> optionalDelete = findById(id);//İlk olarak, id ile veritabanında bir kullanıcı aranır. findById(id) metodu, kullanıcıyı bulup bulmadığını kontrol etmek için Optional döndürür.
        if (optionalDelete.isPresent()) {
            String sql = "DELETE FROM usertable WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) { //PreparedStatement, SQL sorgusunun içine kullanıcı id'sini yerleştirir. Bu, hangi kullanıcıyı sileceğimizi belirtir.
                preparedStatement.setInt(1, id);
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {
                    return optionalDelete; //silinen kullanıcıyı döndürür.
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return Optional.empty();
    }

    @Override
    public UserDTO mapToObjectDTO(ResultSet resultSet) throws SQLException {
        return UserDTO.builder()
                .id(resultSet.getInt("id"))
                .username(resultSet.getString("username"))
                .password(resultSet.getString("password"))
                .email(resultSet.getString("email"))
                .role(ERole.fromString(resultSet.getString("role")))
                .build();
    }

    @Override
    public Optional<UserDTO> selectSingle(String sql, Object... params) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject((i + 1), params[i]);
            }
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapToObjectDTO(resultSet));
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserDTO> loginUser(String usernameOrEmail, String password) {
        String sql = "SELECT * FROM usertable WHERE username = ? OR email = ?";
        Optional<UserDTO> userOpt = selectSingle(sql, usernameOrEmail, usernameOrEmail); //userOpt → Veritabanından username ya da email ile aranan kullanıcı.

        if (userOpt.isPresent()) { //Eğer kullanıcı bulunursa (isPresent()), get() ile kullanıcı nesnesine (UserDTO) erişilir.
            UserDTO user = userOpt.get();
            if (BCrypt.checkpw(password, user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public boolean isUsernameExists(String username) {
        String sql = "SELECT 1 FROM usertable WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // kayıt varsa true döner
        } catch (SQLException e) {
            e.printStackTrace();
            return true; // hata varsa güvenlik için false yerine true döneriz
        }
    }

    public boolean isEmailExists(String email) {
        String sql = "SELECT 1 FROM usertable WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return true; // hata varsa true dön ki işlem durdurulsun
        }
    }


}