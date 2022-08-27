package com.example.eshop.services;

import com.example.eshop.dto.SearchParamsDto;
import com.example.eshop.entities.Product;
import com.example.eshop.exceptions.RepositoryExceptions;
import com.example.eshop.exceptions.ServiceExceptions;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface ProductService extends BaseServices<Product> {
    List<Product> getAllProductsByCategoryId(int categoryId, int pageNumber, int pageSize) throws ServiceExceptions, RepositoryExceptions;

    ModelAndView getProductsBySearchRequest(SearchParamsDto searchParamsDto, int pageNumber, int pageSize) throws ServiceExceptions, RepositoryExceptions;

    ModelAndView getProductById(int id) throws ServiceExceptions, RepositoryExceptions;
}