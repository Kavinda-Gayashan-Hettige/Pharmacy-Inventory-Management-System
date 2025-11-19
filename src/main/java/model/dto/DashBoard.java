package model.dto;

import lombok.*;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class DashBoard {
    private String name;
    private LocalDate expiryDate;
    private int qty;
    private boolean remaining;

}
