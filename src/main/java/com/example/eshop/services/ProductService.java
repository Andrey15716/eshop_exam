package com.example.eshop.services;

import com.example.eshop.entities.Product;
import com.example.eshop.exceptions.RepositoryExceptions;
import com.example.eshop.exceptions.ServiceExceptions;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.List;

public interface ProductService extends BaseServices<Product> {
    List<Product> getAllProductsByCategoryId(int categoryId) throws ServiceExceptions, RepositoryExceptions;

    ModelAndView getProductsBySearchRequest(String param) throws ServiceExceptions, RepositoryExceptions;

    ModelAndView getProductById(int id) throws ServiceExceptions, RepositoryExceptions;

    List<Product> getAllProductsByCategoryIdPagination(int categoryId, int pageNumber) throws ServiceExceptions, RepositoryExceptions;

    long getNumberOfProductsPerPage(int categoryId);

    void downloadCsvFile(Writer writer) throws RepositoryExceptions, ServiceExceptions;

    List<Product> saveProductsFromCsvFile(InputStream inputStream) throws IOException;

}