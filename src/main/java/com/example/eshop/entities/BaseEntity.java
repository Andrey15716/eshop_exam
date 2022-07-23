package com.example.eshop.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@MappedSuperclass
public class BaseEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
}