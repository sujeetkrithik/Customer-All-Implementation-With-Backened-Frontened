package com.sujeet.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerDTO {

    @NotBlank(message = "First Name is mandatory")
    private String first_name;
    @NotBlank(message = "Last Name is mandatory")
    private String last_name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String street;
    private String city;
    private String state;
}
