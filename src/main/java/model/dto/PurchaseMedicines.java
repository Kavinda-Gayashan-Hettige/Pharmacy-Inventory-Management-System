package model.dto;


import lombok.*;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class PurchaseMedicines {

    private String purchaseId;
    private String medicineName;
    private double unitPrice;
    private double discount;
    private LocalDate date;
    private int qty;
    private double subTotal;

    public double getSubTotal() {
        return (unitPrice - discount) * qty;
    }


}
