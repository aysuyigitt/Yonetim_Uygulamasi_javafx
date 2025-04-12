package com.aysuyigit.yonetim_uygulamasi_javafx.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class NoteBookDTO {

    private Integer id;
    private String title;
    private String content;
    private UserDTO userDTO;
    private LocalDateTime timestamp;

    public NoteBookDTO() {

    }

    public NoteBookDTO(Integer id, String title, String content,LocalDateTime timestamp) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.timestamp=timestamp;

    }
}