package com.aysuyigit.yonetim_uygulamasi_javafx.dto;

import com.sun.jdi.PathSearchingVirtualMachine;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor // Parametreli Constructor
@NoArgsConstructor  // Parametresiz Constructor
@ToString
@Builder


public class UserDTO {

    private Integer id;
    private String username;
    private String password;
    private String email;

}

    /*public static void main(String[] args) {
        UserDTO userDTO = UserDTO.builder()
                .id(0)
                .username("username")
                .email("aysuyigit@gmail.com")
                .password("şifre")
                .build();
        System.out.println((userDTO));
    }*/




