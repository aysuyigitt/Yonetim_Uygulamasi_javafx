package com.aysuyigit.yonetim_uygulamasi_javafx.utils;

import com.aysuyigit.yonetim_uygulamasi_javafx.dto.UserDTO;

public class SessionManager {
    private static UserDTO currentUser;

    public static void setCurrentUser(UserDTO user) {
        currentUser = user;
    }

    public static UserDTO getCurrentUser() {
        return currentUser;
    }
}
