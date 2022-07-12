package com.example.eshop.services;

import com.example.eshop.entities.Category;
import org.springframework.web.servlet.ModelAndView;

public interface CategoryService extends BaseServices<Category> {
    ModelAndView getCategoryData(int id);
}