package com.example.eshop.services.impl;

import com.example.eshop.entities.Category;
import com.example.eshop.entities.Product;
import com.example.eshop.exceptions.RepositoryExceptions;
import com.example.eshop.exceptions.ServiceExceptions;
import com.example.eshop.repositories.CategoryRepository;
import com.example.eshop.services.CategoryService;
import com.example.eshop.services.ProductService;
import com.example.eshop.utils.Assertions;
import com.example.eshop.utils.CsvParser;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.eshop.utils.PagesPathEnum.CATEGORY_PAGE;
import static com.example.eshop.utils.RequestParamsEnum.CATEGORY_PARAM;
import static com.example.eshop.utils.RequestParamsEnum.PAGE_NUMBER_PARAM;

@Slf4j
@Service
public class CategoryServiceImpl<C> implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductService productService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductService productService, CsvParser csvUtil) {
        this.categoryRepository = categoryRepository;
        this.productService = productService;
    }

    @Override
    public Category create(Category entity) throws ServiceExceptions, RepositoryExceptions {
        return categoryRepository.create(entity);
    }

    @Override
    public List<Category> read() throws ServiceExceptions, RepositoryExceptions {
        return categoryRepository.read();
    }

    @Override
    public Category update(Category entity) throws ServiceExceptions, RepositoryExceptions {
        return categoryRepository.update(entity);
    }

    @Override
    public void delete(int id) throws ServiceExceptions, RepositoryExceptions {
        categoryRepository.delete(id);
    }

    @Override
    public ModelAndView getCategoryData(int id) throws ServiceExceptions, RepositoryExceptions {
        ModelMap modelMap = new ModelMap();
        Category category = categoryRepository.getCategoryById(id);
        if (Optional.ofNullable(category).isPresent()) {
            List<Product> products = productService.getAllProductsByCategoryId(category.getId());
            category.setProductList(products);
            modelMap.addAttribute(CATEGORY_PARAM.getValue(), category);
            log.info("User got categoryData by id - " + id);
        }
        return new ModelAndView(CATEGORY_PAGE.getPath(), modelMap);
    }

    @Override
    public ModelAndView getCategoryDataPagination(int id, int pageNumber) throws ServiceExceptions, RepositoryExceptions {
        ModelMap modelMap = new ModelMap();
        Category category = categoryRepository.getCategoryById(id);
        if (Optional.ofNullable(category).isPresent()) {
            List<Product> products = productService.getAllProductsByCategoryIdPagination(category.getId(), pageNumber);
            category.setProductList(products);
            long numberPages = productService.getNumberOfProductsPerPage(id);
            List<Long> listPages = new ArrayList<>();
            for (long i = 1; i <= numberPages; i++) {
                listPages.add(i);
            }
            modelMap.addAttribute(PAGE_NUMBER_PARAM.getValue(), listPages);
            modelMap.addAttribute(CATEGORY_PARAM.getValue(), category);
        }
        return new ModelAndView(CATEGORY_PAGE.getPath(), modelMap);
    }

    @Override
    public void downloadCsvFile(Writer writer) throws RepositoryExceptions {
        List<Category> categories = categoryRepository.read();
        try {
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();
            beanToCsv.write(categories);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public List<Category> saveCategoriesFromCsvFile(InputStream inputStream) {
        Assertions.assertNonNull(inputStream, "CSV parser not provided");
        List<Category> categoryParserCsv = CsvParser.categoryParserCsv(inputStream);
        if (Optional.ofNullable(categoryParserCsv).isPresent()) {
            categoryParserCsv.forEach(entity -> {
                try {
                    categoryRepository.create(entity);
                } catch (RepositoryExceptions e) {
                    log.error("Entity from category parser csv was not saved");
                }
            });
        }
        return Collections.emptyList();
    }
}
