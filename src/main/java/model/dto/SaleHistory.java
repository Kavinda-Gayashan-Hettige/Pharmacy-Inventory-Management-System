package model.dto;

import lombok.*;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class SaleHistory {
    private int invoiceNo;
    private LocalDate date;
    private String customerName;
    private double total;
    private String paymentType;
}
