package com.example.eshop.repositories;

import com.example.eshop.entities.Category;
import com.example.eshop.exceptions.RepositoryExceptions;

public interface CategoryRepository extends BaseRepository<Category> {
    Category getCategoryById(int id) throws RepositoryExceptions;
}