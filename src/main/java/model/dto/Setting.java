package model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Setting {
    private int lowStockThreshold;
    private int expiryAlertDays;
    private String pharmacyName;
    private String address;
    private String phone;
    private String email;
}
