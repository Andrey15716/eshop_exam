package com.example.eshop.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class User extends BaseEntity {
    private String name;
    private String surname;
    @Size(min = 3, max = 6, message = "Incorrect password! Password must contain at least 3 to 6 characters ")
    @Pattern(regexp = "\\S+", message = "Spaces are not allowed there")
    private String password;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateBorn;

    public User(int id, String name, String surname, String password, LocalDate dateBorn) {
        super(id);
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.dateBorn = dateBorn;
    }
}