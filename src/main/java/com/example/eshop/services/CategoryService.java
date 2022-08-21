package com.example.eshop.services;

import com.example.eshop.entities.Category;
import com.example.eshop.exceptions.RepositoryExceptions;
import com.example.eshop.exceptions.ServiceExceptions;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.List;

public interface CategoryService extends BaseServices<Category> {
    ModelAndView getCategoryData(int id) throws ServiceExceptions, RepositoryExceptions;

    ModelAndView getCategoryDataPagination(int id, int pageNumber) throws ServiceExceptions, RepositoryExceptions;

    void downloadCsvFile(Writer writer) throws RepositoryExceptions, ServiceExceptions;

    List<Category> saveCategoriesFromCsvFile(InputStream inputStream) throws IOException;
}