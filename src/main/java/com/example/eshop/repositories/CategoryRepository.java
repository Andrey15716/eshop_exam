package com.example.eshop.repositories;

import com.example.eshop.entities.Category;

public interface CategoryRepository extends BaseRepository<Category> {
    Category getCategoryById(int id);
}