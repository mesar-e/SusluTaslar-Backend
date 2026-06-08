package com.CrystalShop.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {

    @NotBlank(message = "İsim boş bırakılamaz")
    private String firstName;

    @NotBlank(message = "Soyisim boş bırakılamaz")
    private String lastName;

    @NotBlank(message = "E-posta adresi zorunludur")
    @Email(message = "Lütfen geçerli bir e-posta adresi giriniz")
    private String email;

    @NotBlank(message = "Şifre boş bırakılamaz")
    @Size(min = 6, message = "Şifreniz en az 6 karakter olmalıdır")
    private String password;
}