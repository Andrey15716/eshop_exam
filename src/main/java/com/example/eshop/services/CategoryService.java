package com.example.eshop.services;

import com.example.eshop.entities.Category;
import com.example.eshop.exceptions.RepositoryExceptions;
import com.example.eshop.exceptions.ServiceExceptions;
import org.springframework.web.servlet.ModelAndView;

public interface CategoryService extends BaseServices<Category> {
    ModelAndView getCategoryData(int id, int pageNumber, int pageSize) throws ServiceExceptions, RepositoryExceptions;
}