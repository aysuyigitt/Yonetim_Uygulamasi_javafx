package com.aysuyigit.yonetim_uygulamasi_javafx.exceptions;

public class RegisterNotFoundException extends RuntimeException {
  // Parametresiz Constructor
  public RegisterNotFoundException() {
    super("Kayıt bulunamadı");
  }

  // Parametreli Constructor
  public RegisterNotFoundException(String message) {
    super(message);
  }
}
