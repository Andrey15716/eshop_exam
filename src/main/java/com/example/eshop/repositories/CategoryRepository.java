package com.example.eshop.repositories;

import com.example.eshop.entities.Category;
import com.example.eshop.exceptions.RepositoryExceptions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category getCategoryById(int id) throws RepositoryExceptions;
}