package model.dto;

import lombok.*;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class SalePOS {
    private String selID;
    private String medicineName;
    private double unitPrice;
    private double discount;
    private LocalDate date;
    private int qty;

}
