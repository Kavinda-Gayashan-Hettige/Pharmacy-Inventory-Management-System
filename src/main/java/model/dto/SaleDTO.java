package model.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class SaleDTO {
    private int saleId;
    private String customerName;
    private List<SaleItemDTO> items;
    private BigDecimal totalAmount;
    private BigDecimal paidAmount;

    public BigDecimal getBalance() {
        return totalAmount.subtract(paidAmount);
    }

}
