package model.dto;

import com.sun.javafx.beans.IDProperty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Suppliers {

    private String supplierId;
    private String name;
    private String contactPerson;
    private String phone;
    private String email;
    private String address;
}
