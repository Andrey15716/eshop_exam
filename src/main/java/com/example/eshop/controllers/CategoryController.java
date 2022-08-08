package com.example.eshop.controllers;

import com.example.eshop.exceptions.RepositoryExceptions;
import com.example.eshop.exceptions.ServiceExceptions;
import com.example.eshop.services.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public ModelAndView openCategoryPage(@PathVariable int id) throws ServiceExceptions, RepositoryExceptions {
        return categoryService.getCategoryData(id);
    }

    @GetMapping("/{id}/{page}")
    public ModelAndView openCategoryPagination(@PathVariable int id, @PathVariable int page) throws ServiceExceptions, RepositoryExceptions {
        return categoryService.getCategoryDataPagination(id, page);
    }
}