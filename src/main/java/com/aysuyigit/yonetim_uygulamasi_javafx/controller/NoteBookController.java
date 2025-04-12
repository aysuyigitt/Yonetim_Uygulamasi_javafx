package com.aysuyigit.yonetim_uygulamasi_javafx.controller;

import com.aysuyigit.yonetim_uygulamasi_javafx.dao.NoteBookDAO;
import com.aysuyigit.yonetim_uygulamasi_javafx.dto.NoteBookDTO;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import java.util.List;
import java.util.Optional;

public class NoteBookController {

    private NoteBookDAO noteBookDAO;

    @FXML
    private ListView<NoteBookDTO> noteListView;

    @FXML
    private TextField titleField;

    @FXML
    private TextArea contentArea;

    @FXML
    private Button addButton, updateButton, deleteButton;

    public NoteBookController() {
        this.noteBookDAO = new NoteBookDAO();
    }

    //  Yeni not defteri oluştur
    @FXML
    public void handleAddNote() {
        String title = titleField.getText();
        String content = contentArea.getText();

        if (title.isBlank() || content.isBlank()) {
            showAlert(AlertType.WARNING, "Başlık ve içerik boş olamaz.");
            return;
        }

        NoteBookDTO newNote = new NoteBookDTO();
        newNote.setTitle(title);  // Başlık burada atanıyor
        newNote.setContent(content);  // İçerik burada atanıyor

        if (createNote(newNote)) {
            loadNotes();
            clearFields();
            showAlert(AlertType.INFORMATION, "Veritabanına başarıyla not defteri eklendi.");
        }
    }

    @FXML
    //  Not ekleme işlemi
    public boolean createNote(NoteBookDTO noteBookDTO) {
        Optional<NoteBookDTO> createdNote = noteBookDAO.create(noteBookDTO);
        if (createdNote.isPresent()) {
            return true; // Başarıyla oluşturuldu
        } else {
            showAlert(AlertType.ERROR, "Not oluşturulamadı.");
            return false; // Oluşturulamadı
        }
    }

    //  Not güncelle
    @FXML
    public void handleUpdateNote() {
        NoteBookDTO selectedNote = noteListView.getSelectionModel().getSelectedItem();
        if (selectedNote != null) {
            selectedNote.setTitle(titleField.getText());
            selectedNote.setContent(contentArea.getText());
            if (updateNote(selectedNote.getId(), selectedNote)) {
                loadNotes();
                clearFields();
            }
        } else {
            showAlert(AlertType.WARNING, "Lütfen güncellemek için bir not seçin.");
        }
    }

    //  Not güncelleme işlemi
    public boolean updateNote(int id, NoteBookDTO updatedNote) {
        Optional<NoteBookDTO> updated = noteBookDAO.update(id, updatedNote);
        if (updated.isPresent()) {
            return true; // Başarıyla güncellendi
        } else {
            showAlert(AlertType.ERROR, "Not güncellenemedi.");
            return false; // Güncellenemedi
        }
    }

    //  Not sil
    @FXML
    public void handleDeleteNote() {
        NoteBookDTO selectedNote = noteListView.getSelectionModel().getSelectedItem();
        if (selectedNote != null) {
            if (deleteNote(selectedNote.getId())) {
                loadNotes();
                clearFields();
            }
        } else {
            showAlert(AlertType.WARNING, "Lütfen silmek için bir not seçin.");
        }
    }

    //  Not silme işlemi
    public boolean deleteNote(int id) {
        Optional<NoteBookDTO> deleted = noteBookDAO.delete(id);
        if (deleted.isPresent()) {
            return true; // Başarıyla silindi
        } else {
            showAlert(AlertType.ERROR, "Not silinemedi.");
            return false; // Silinemedi
        }
    }

    //  Kullanıcıya notları yükle
    private void loadNotes() {
        Optional<List<NoteBookDTO>> notes = noteBookDAO.listByUserId(1);  // Örneğin kullanıcı ID'si 1
        noteListView.getItems().clear();
        //noteListView.getItems().addAll(notes);
    }

    //  Alanları temizle
    private void clearFields() {
        titleField.clear();
        contentArea.clear();
        noteListView.getSelectionModel().clearSelection();
    }

    //  Uyarı mesajı göster
    private void showAlert(AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Uyarı");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
