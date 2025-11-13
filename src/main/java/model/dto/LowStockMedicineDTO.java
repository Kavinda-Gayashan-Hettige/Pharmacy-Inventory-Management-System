package model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class LowStockMedicineDTO {
    private String name;
    private int remainingQty;

}
