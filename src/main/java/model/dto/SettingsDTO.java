package model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class SettingsDTO {
    private int lowStockThreshold;
    private int expiryAlertDays;
    private String pharmacyName;
    private String address;
    private String phone;
    private String email;

}

