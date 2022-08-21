package com.example.eshop.services.impl;

import com.example.eshop.entities.Product;
import com.example.eshop.exceptions.RepositoryExceptions;
import com.example.eshop.exceptions.ServiceExceptions;
import com.example.eshop.repositories.ProductRepository;
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

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.eshop.utils.PagesPathEnum.PRODUCT_PAGE;
import static com.example.eshop.utils.PagesPathEnum.SEARCH_PAGE;
import static com.example.eshop.utils.RequestParamsEnum.PRODUCT_PARAM;
import static com.example.eshop.utils.RequestParamsEnum.SEARCH_RESULT_PARAM;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product create(Product entity) throws ServiceExceptions, RepositoryExceptions {
        return productRepository.create(entity);
    }

    @Override
    public List<Product> read() throws ServiceExceptions, RepositoryExceptions {
        return productRepository.read();
    }

    @Override
    public Product update(Product entity) throws ServiceExceptions, RepositoryExceptions {
        return productRepository.update(entity);
    }

    @Override
    public void delete(int id) throws ServiceExceptions, RepositoryExceptions {
        productRepository.delete(id);
    }

    @Override
    public List<Product> getAllProductsByCategoryId(int categoryId) throws RepositoryExceptions {
        return productRepository.getAllProductsByCategoryId(categoryId);
    }

    @Override
    public ModelAndView getProductsBySearchRequest(String param) throws ServiceExceptions, RepositoryExceptions {
        ModelMap modelMap = new ModelMap();
        List<Product> productListResult = productRepository.getProductsBySearchRequest(param);
        modelMap.addAttribute(SEARCH_RESULT_PARAM.getValue(), productListResult);
        log.info("User got product by search request - " + param);
        return new ModelAndView(SEARCH_PAGE.getPath(), modelMap);
    }

    @Override
    public ModelAndView getProductById(int id) throws ServiceExceptions, RepositoryExceptions {
        ModelMap modelMap = new ModelMap();
        Product product = productRepository.getProductById(id);
        if (Optional.ofNullable(product).isPresent()) {
            modelMap.addAttribute(PRODUCT_PARAM.getValue(), product);
            log.info("User got product by id - " + id);
        }
        return new ModelAndView(PRODUCT_PAGE.getPath(), modelMap);
    }

    @Override
    public List<Product> getAllProductsByCategoryIdPagination(int categoryId, int pageNumber) throws ServiceExceptions, RepositoryExceptions {
        return productRepository.getAllProductsByCategoryIdPaging(categoryId, pageNumber);
    }

    @Override
    public long getNumberOfProductsPerPage(int categoryId) {
        return productRepository.getNumberOfProductsPerPage(categoryId);
    }

    @Override
    public void downloadCsvFile(Writer writer) throws RepositoryExceptions, ServiceExceptions {
        List<Product> products = productRepository.read();
        try {
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();
            beanToCsv.write(products);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public List<Product> saveProductsFromCsvFile(InputStream inputStream) throws IOException {
        Assertions.assertNonNull(inputStream, "CSV parser not provided");
        List<Product> productsParserCsv = CsvParser.productsParserCsv(inputStream);
        if (Optional.ofNullable(productsParserCsv).isPresent()) {
            productsParserCsv.forEach(entity -> {
                try {
                    productRepository.create(entity);
                } catch (RepositoryExceptions e) {
                    log.error("Entity from category parser csv was not saved");
                }
            });
        }
        return Collections.emptyList();
    }
}