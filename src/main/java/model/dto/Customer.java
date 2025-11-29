package model.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Customer {
    private String customerID;
    private String customerName;
    private double salePrice;
    private String medicine_name;
    private int qty;
    private LocalDate date;
}
