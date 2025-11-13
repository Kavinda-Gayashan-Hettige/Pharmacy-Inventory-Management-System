package model.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class DashboardDTO {
    private int totalMedicinesCount;
    private int lowStockCount;
    private int expiringSoonCount;
    private BigDecimal todaysSalesTotal;
    private List<ExpiringMedicineDTO> expiringMedicines;
    private List<LowStockMedicineDTO> lowStockMedicines;
    private List<NotificationDTO> notifications;

}
