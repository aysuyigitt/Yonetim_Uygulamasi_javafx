package com.aysuyigit.yonetim_uygulamasi_javafx.controller;

import com.aysuyigit.yonetim_uygulamasi_javafx.dao.UserDAO;
import com.aysuyigit.yonetim_uygulamasi_javafx.dto.UserDTO;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import java.util.List;
import java.util.Optional;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.*;


public class AdminController {

    private UserDAO userDAO;

    //Parametresiz construcutor
    public AdminController() {
        userDAO = new UserDAO();
    }

    //Kullanıcı tablo ve sütun
    @FXML
    private TableView<UserDTO> userTable;

    @FXML
    private TableColumn<UserDTO, String> idColumn;

    @FXML
    private TableColumn<UserDTO, String> usernameColumn;

    @FXML
    private TableColumn<UserDTO, String> emailColumn;

    @FXML
    private TableColumn<UserDTO, String> passwordColumn;

    //Admin sayfası açılırken ayağa kalk
    @FXML
    public void initialize() {

        // KULLANICI
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        passwordColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String password, boolean empty) {
                super.updateItem(password, empty);
                if (empty || password == null) {
                    setText(null);
                } else {
                    setText("*****");
                }
            }
        });
        refreshTable();
    }

    //Kullanıcı listesini veritbanından alıp tabloyu güünceller
    @FXML
    private void refreshTable() {
        //Kullanıcı listesini Optional olarak al
        Optional<List<UserDTO>> optionalUsers = userDAO.list();//kullanıcı listesini getirme

        //Optional al ve eğer boşsa bir liste kullan
        List<UserDTO> userDTOList = optionalUsers.orElseGet(List::of);
        ObservableList<UserDTO> userDTOObservableList = FXCollections.observableList((userDTOList));

        //Tabloyu yükle
        userTable.setItems((userDTOObservableList));

        showAlert("Bilgi", "Tablo başarıyla yenilendi!", Alert.AlertType.INFORMATION);
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void logout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Çıkış Yap");
        alert.setHeaderText("Oturumdan çıkmak istiyor musunuz?");
        alert.setContentText("Emin misiniz?");
        Optional<ButtonType> result = alert.showAndWait();
        //Eğer kullanıcıdan Ok geldiyse
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource
                        ("/com/aysuyigit/yonetim_uygulamasi_javafx/view/login.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) userTable.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                showAlert("Hata", "Giriş sayfasına yönlendirme başarısız!", Alert.AlertType.ERROR);
            }
        }
    }


    public void addUser(ActionEvent actionEvent) {

        TextInputDialog usernameDialog = new TextInputDialog();
        usernameDialog.setTitle("Yeni Kullanıcı Ekle");
        usernameDialog.setHeaderText("Yeni Kullanıcı Ekle");
        usernameDialog.setContentText("Kullanıcı Adı");

        //Username verisi varsa
        Optional<String> optionalUsername = usernameDialog.showAndWait();

        //Text Input içine veri girilmişse
        if (optionalUsername.isPresent()) {
            String username = optionalUsername.get();

            //Şifre için input
            TextInputDialog passwordDialog = new TextInputDialog();
            passwordDialog.setTitle("Yeni Kullanıcı Şifre");
            passwordDialog.setHeaderText("Yeni Kullanıcı Şifre");
            passwordDialog.setContentText("Kullanıcı Şifre");

            //Username verisi varsa
            Optional<String> optionalPassword = passwordDialog.showAndWait();

            if (optionalPassword.isPresent()) {
                String password = optionalPassword.get();

                //Email için input
                TextInputDialog emailDialog = new TextInputDialog();
                passwordDialog.setTitle("Yeni Kullanıcı Email");
                passwordDialog.setHeaderText("Yeni Kullanıcı Email");
                passwordDialog.setContentText("Kullanıcı Email");

                Optional<String> optionalEmail = emailDialog.showAndWait();

                if (optionalEmail.isPresent()) {
                    String email = optionalPassword.get();

                    //Kullanıcı Ekleme
                    Optional<UserDTO> newUser = Optional.of(new UserDTO(0, username, password, email));

                    //Optional içindeki değeri almak istiyorsak
                    newUser.ifPresent(user -> {
                        Optional<UserDTO> createdUser = userDAO.create(user);
                        if (createdUser.isPresent()) {
                            showAlert("Başarılı", "Kullanıcı Başarıyla Eklendi", Alert.AlertType.INFORMATION);
                            refreshTable();
                        } else {
                            showAlert("Başarısız", "Kullanıcı Eklerken Hata Alındı", Alert.AlertType.ERROR);
                        }
                    }); //end newUser.ifPresent
                } //end  if(optionalEmail.isPresent())
            } //end if(optionalPassword.isPresent())
        } //end if(optionalUsername.isPresent())
    } //end public void addUser(ActionEvent actionEvent)


    public void updateUser(ActionEvent actionEvent) {
        //Seçilen kullanıcıyı güncelle
        UserDTO selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            //Kullanıcı güncelleme
            TextInputDialog usernameDialog = new TextInputDialog();
            usernameDialog.setTitle("Kullanıcı Güncelle");
            usernameDialog.setHeaderText("Kullanıcı Güncelle");
            usernameDialog.setContentText("Kullanıcı Adı");

            //Username verisi varsa
            Optional<String> optionalUsername = usernameDialog.showAndWait();

            //Text Input içine veri girilmişse
            if (optionalUsername.isPresent()) {
                String username = optionalUsername.get();

                //Şifre için input
                TextInputDialog passwordDialog = new TextInputDialog();
                passwordDialog.setTitle("Güncelle Kullanıcı Şifre");
                passwordDialog.setHeaderText("Güncelle Kullanıcı Şifre");
                passwordDialog.setContentText("Kullanıcı Şifre");

                //Username verisi varsa
                Optional<String> optionalPassword = passwordDialog.showAndWait();

                if (optionalPassword.isPresent()) {
                    String password = optionalPassword.get();

                    //Email için input
                    TextInputDialog emailDialog = new TextInputDialog();
                    passwordDialog.setTitle("Güncelle Kullanıcı Email");
                    passwordDialog.setHeaderText("Güncelle Kullanıcı Email");
                    passwordDialog.setContentText("Kullanıcı Email");

                    Optional<String> optionalEmail = emailDialog.showAndWait();

                    if (optionalEmail.isPresent()) {
                        String email = optionalPassword.get();

                        //Kullanıcı Ekleme
                        Optional<UserDTO> newUser = Optional.of(new UserDTO(0, username, password, email));

                        //Optional içindeki değeri almak istiyorsak
                        newUser.ifPresent(user -> {
                            Optional<UserDTO> createdUser = userDAO.update(selectedUser.getId(), selectedUser);
                            if (createdUser.isPresent()) {
                                showAlert("Başarılı", "Kullanıcı Başarıyla Güncellendi", Alert.AlertType.INFORMATION);
                                refreshTable();
                            } else {
                                showAlert("Başarısız", "Kullanıcı Güncellenirken Hata Alındı", Alert.AlertType.ERROR);
                            }
                        }); //end newUser.ifPresent
                    } //end  if(optionalEmail.isPresent())
                } //end if(optionalPassword.isPresent())
            } //end if(optionalUsern
        } else {
            showAlert("Uyarı", "Lütfen bir kullanıcı seçiniz", Alert.AlertType.ERROR);
        }
    }

    public void deleteUser(ActionEvent actionEvent) {
        //Seçilen kullanıcıyı sil
        //Tıklanmış kullanıcı bilgisini almak
        Optional<UserDTO> selectedUser = Optional.ofNullable(userTable.getSelectionModel().getSelectedItem());
        if (selectedUser != null) {

            //Onay Penceresi
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Silme Onayı");
            confirmationAlert.setHeaderText("Kullanıcı silmek ister misiniz");
            confirmationAlert.setContentText("Silinecek Kullanıcı" + selectedUser.get().getUsername());

            //Kullanıcı Onayını Almak
            Optional<ButtonType> isDelete = confirmationAlert.showAndWait();
            if (isDelete.isPresent() && isDelete.get() == ButtonType.OK) {

                selectedUser.ifPresent(user -> {
                    Optional<UserDTO> deleteUser = userDAO.delete(selectedUser.get().getId());
                    if (deleteUser.isPresent()) {
                        showAlert("Başarılı", "Kullanıcı Başarıyla Silindi", Alert.AlertType.INFORMATION);
                        refreshTable();
                    } else {
                        showAlert("Başarısız", "Kullanıcı Silinirken Hata Alındı", Alert.AlertType.ERROR);
                    }
                }); //end newUser.ifPresent
            } //end  if(optionalEmail.isPres
        }
    }



    public void refleshTable(ActionEvent actionEvent) {
    }

    public void logout(ActionEvent actionEvent) {
    }

}