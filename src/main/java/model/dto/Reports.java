package model.dto;

import lombok.*;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Reports {
    private String reportType; // e.g., "Daily", "Monthly", "Stock", "Expiry"
    private LocalDate fromDate;
    private LocalDate toDate;
}
