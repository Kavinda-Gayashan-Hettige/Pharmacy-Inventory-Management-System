package model.dto;

import lombok.*;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class PurchaseHistory {
    private int invoiceNo;
    private LocalDate date;
    private String supplierName;
    private double total;
    private String paymentType;
}
