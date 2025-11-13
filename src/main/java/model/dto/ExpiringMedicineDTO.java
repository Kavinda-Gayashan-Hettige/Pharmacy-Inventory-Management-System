package model.dto;

import lombok.*;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ExpiringMedicineDTO {
    private String name;
    private LocalDate expiryDate;
    private int quantity;

}
