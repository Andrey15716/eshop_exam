package com.example.eshop.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "categories")
public class Category extends BaseEntity {
    @Column(name = "id")
    private int categoryId;
    @Column
    private String name;
    @Column(name = "image")
    private String imageName;
    @OneToMany(mappedBy = "category", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Product> productList;
}