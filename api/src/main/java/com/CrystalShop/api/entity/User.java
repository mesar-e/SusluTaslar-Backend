package com.CrystalShop.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "boş bırakılamaz")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "boş bırakılamaz")
    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "E-posta adresi zorunludur")
    @Email(message = "Lütfen geçerli bir e-posta adresi giriniz")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "Şifre boş bırakılamaz")
    @Size(min = 6, message = "Şifreniz en az 6 karakter olmalıdır")
    @Column(name = "pass")
    private String password;
}
