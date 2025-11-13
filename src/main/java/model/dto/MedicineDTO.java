package model.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class MedicineDTO {
    private int medicineId;
    private String name;
    private String brand;
    private String batchNo;
    private LocalDate expiryDate;
    private int quantity;
    private BigDecimal purchasePrice;
    private BigDecimal sellingPrice;
    private String supplier;

}