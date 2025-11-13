package model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class SupplierDTO {
    private int supplierId;
    private String name;
    private String contactPerson;
    private String phone;
    private String email;
    private String address;

}
