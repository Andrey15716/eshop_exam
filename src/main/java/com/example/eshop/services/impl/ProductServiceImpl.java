package com.example.eshop.services.impl;

import com.example.eshop.dto.SearchParamsDto;
import com.example.eshop.entities.Product;
import com.example.eshop.exceptions.RepositoryExceptions;
import com.example.eshop.exceptions.ServiceExceptions;
import com.example.eshop.repositories.ProductRepository;
import com.example.eshop.repositories.ProductSearchSpecification;
import com.example.eshop.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

import static com.example.eshop.utils.EshopConstants.SEARCH_PARAM;
import static com.example.eshop.utils.PagesPathEnum.PRODUCT_PAGE;
import static com.example.eshop.utils.PagesPathEnum.SEARCH_PAGE;
import static com.example.eshop.utils.RequestParamsEnum.IS_FIRST_PAGE;
import static com.example.eshop.utils.RequestParamsEnum.IS_LAST_PAGE;
import static com.example.eshop.utils.RequestParamsEnum.NUMBER_OF_PAGES;
import static com.example.eshop.utils.RequestParamsEnum.PAGE_NUMBER;
import static com.example.eshop.utils.RequestParamsEnum.PAGE_SIZE;
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
        return productRepository.save(entity);
    }

    @Override
    public List<Product> read() {
        return productRepository.findAll();
    }

    @Override
    public Product update(Product entity) {
        return productRepository.save(entity);
    }

    @Override
    public void delete(int id) {
        productRepository.deleteProductById(id);
    }

    @Override
    public List<Product> getAllProductsByCategoryId(int categoryId, int pageNumber, int pageSize) throws RepositoryExceptions {
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending());
        Page<Product> products = productRepository.findAllByCategoryId(categoryId, paging);
        log.info("Products has been found");
        return products.getContent();
    }

    @Override
    public ModelAndView getProductsBySearchRequest(SearchParamsDto searchParamsDto, int pageNumber, int pageSize) throws ServiceExceptions, RepositoryExceptions {
        ModelMap modelMap = new ModelMap();
        ProductSearchSpecification productSearchSpecification = new ProductSearchSpecification(searchParamsDto);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("name"));
        Page<Product> productsPage = productRepository.findAll(productSearchSpecification, pageable);
        List<Product> productListResult = productRepository.findAll(productSearchSpecification);
        modelMap.addAttribute(SEARCH_RESULT_PARAM.getValue(), productListResult);
        modelMap.addAttribute(NUMBER_OF_PAGES.getValue(), productsPage.getTotalPages());
        modelMap.addAttribute(PAGE_SIZE.getValue(), pageSize);
        modelMap.addAttribute(IS_FIRST_PAGE.getValue(), productsPage.isFirst());
        modelMap.addAttribute(PAGE_NUMBER.getValue(), pageNumber);
        modelMap.addAttribute(IS_LAST_PAGE.getValue(), productsPage.isLast());
        modelMap.addAttribute(SEARCH_PARAM, searchParamsDto);
        log.info("User got product by search request - " + searchParamsDto.getSearchKey());
        return new ModelAndView(SEARCH_PAGE.getPath(), modelMap);
    }

    @Override
    public ModelAndView getProductById(int id) throws RepositoryExceptions {
        ModelMap modelMap = new ModelMap();
        Product product = productRepository.getProductById(id);
        if (Optional.ofNullable(product).isPresent()) {
            modelMap.addAttribute(PRODUCT_PARAM.getValue(), product);
            log.info("User got product by id - " + id);
        }
        return new ModelAndView(PRODUCT_PAGE.getPath(), modelMap);
    }
}