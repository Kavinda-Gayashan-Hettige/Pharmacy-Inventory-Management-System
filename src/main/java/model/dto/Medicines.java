package model.dto;

import lombok.*;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Medicines {
    private String medicineId;
    private String name;
    private String brand;
    private String batchNo;
    private LocalDate expiryDate;
    private int quantity;
    private double purchasePrice;
    private double sellingPrice;
    private String supplier;
    private boolean remaining;
}
