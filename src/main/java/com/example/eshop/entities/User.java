package com.example.eshop.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "user")
public class User extends BaseEntity {
    @Column(name = "user_id")
    private int id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    @Size(min = 3, max = 6, message = "Incorrect password! Password must contain at least 3 to 6 characters ")
    @Pattern(regexp = "\\S+", message = "Spaces are not allowed there")
    private String password;
    @Column(name = "date_of_birthday")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateBorn;
}