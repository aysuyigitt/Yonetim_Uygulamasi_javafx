package com.aysuyigit.yonetim_uygulamasi_javafx.dao;

import com.aysuyigit.yonetim_uygulamasi_javafx.database.SingletonDBConnection;
import com.aysuyigit.yonetim_uygulamasi_javafx.dto.NoteBookDTO;
import com.aysuyigit.yonetim_uygulamasi_javafx.dto.UserDTO;
import com.aysuyigit.yonetim_uygulamasi_javafx.utils.ERole;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;


public class NoteBookDAO implements IDaoImplements<NoteBookDTO>{

    private Connection connection;

    public NoteBookDAO() {
        this.connection = SingletonDBConnection.getInstance().getConnection();
    }

    @Override
    public Optional<NoteBookDTO> create(NoteBookDTO noteBookDTO) {
        if (noteBookDTO.getTitle() == null || noteBookDTO.getTitle().isEmpty()) {
            System.out.println("Title is required and cannot be empty.");
            return Optional.empty();
        }

        String sql = "INSERT INTO notebook_table (title, content) values (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, noteBookDTO.getTitle());
            ps.setString(2, noteBookDTO.getContent());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        noteBookDTO.setId(generatedKeys.getInt(1));
                        return Optional.of(noteBookDTO);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }


    @Override
    public Optional<List<NoteBookDTO>> list() {
        return Optional.empty();
    }

    @Override
    public Optional<NoteBookDTO> findByName(String name) {
        String sql = "SELECT * FROM notebook_table WHERE title = ?";
        return selectSingle(sql, name);
    }

    @Override
    public Optional<NoteBookDTO> findById(int id) {
        String sql = "SELECT * FROM notebook_table WHERE id = ?";
        return selectSingle(sql, id);
    }

    @Override
    public Optional<NoteBookDTO> update(int id, NoteBookDTO noteBookDTO) {
        Optional<NoteBookDTO> existing = findById(id);
        if (existing.isPresent()) {
            String sql = "UPDATE notebook_table SET title = ?, content = ? WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, noteBookDTO.getTitle());
                ps.setString(2, noteBookDTO.getContent());
                ps.setInt(3, id);

                int affected = ps.executeUpdate();
                if (affected > 0) {
                    noteBookDTO.setId(id);
                    return Optional.of(noteBookDTO);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }


    @Override
    public Optional<NoteBookDTO> delete(int id) {
        Optional<NoteBookDTO> existing = findById(id);
        if (existing.isPresent()) {
            String sql = "DELETE FROM notebook_table WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, id);
                int affected = ps.executeUpdate();
                if (affected > 0) {
                    return existing;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }
    @Override
    public NoteBookDTO mapToObjectDTO(ResultSet resultSet) throws SQLException {
        // Veritabanında user_id olmadığı için sadece NoteBookDTO'yu oluşturuyoruz.
        return NoteBookDTO.builder()
                .id(resultSet.getInt("id"))
                .title(resultSet.getString("title"))
                .content(resultSet.getString("content"))
                .build();

    }

    @Override
    public Optional<NoteBookDTO> selectSingle(String sql, Object... params) {
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
    public Optional<NoteBookDTO> loginUser(String username, String password) {
        return Optional.empty();
    }

    public Optional<List<NoteBookDTO>> listByUserId(int userId) {
        List<NoteBookDTO> noteBookDTOList = new ArrayList<>();
        String sql = "SELECT * FROM notebook_table WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NoteBookDTO note = mapToObjectDTO(rs);
                note.setUserDTO(UserDTO.builder().id(userId).build()); // opsiyonel
                noteBookDTOList.add(note);
            }
            return noteBookDTOList.isEmpty() ? Optional.empty() : Optional.of(noteBookDTOList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
