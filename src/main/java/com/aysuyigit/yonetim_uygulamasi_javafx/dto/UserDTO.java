package com.aysuyigit.yonetim_uygulamasi_javafx.dto;

import com.aysuyigit.yonetim_uygulamasi_javafx.utils.ERole;
import com.sun.jdi.PathSearchingVirtualMachine;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor  // Parametresiz Constructor
@ToString
@Builder


public class UserDTO {

    private Integer id;
    private String username;
    private String password;
    private String email;
    private ERole role;

    public UserDTO(Integer id, String username, String password, String email,ERole role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role=role;

    }
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




