package model.dto;

import lombok.*;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class NotificationDTO {
    private String message;
    private LocalDateTime timestamp;

}