package model.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class SalesHistoryDTO {
    private int invoiceNo;
    private LocalDate date;
    private String customerName;
    private BigDecimal total;
    private BigDecimal payment;
    private List<SaleItemDTO> items;

}

