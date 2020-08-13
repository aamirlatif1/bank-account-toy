package com.dkb.bankaccount.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateAccountRequest {
    @NotBlank
    private String firstName;
    private String lastName;
    private String address;
    private String identificationNumber;
}
