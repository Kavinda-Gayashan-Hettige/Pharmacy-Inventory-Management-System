package model.dto;

import lombok.*;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ReportRequestDTO {
    private String reportType; // e.g., "Daily", "Monthly", "Stock", "Expiry"
    private LocalDate fromDate;
    private LocalDate toDate;

}