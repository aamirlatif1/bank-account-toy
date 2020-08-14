package com.dkb.bankaccount.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AccountCreateRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String address;

}
