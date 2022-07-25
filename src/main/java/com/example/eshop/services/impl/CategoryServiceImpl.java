package com.example.eshop.services.impl;

import com.example.eshop.entities.Category;
import com.example.eshop.entities.Product;
import com.example.eshop.exceptions.RepositoryExceptions;
import com.example.eshop.exceptions.ServiceExceptions;
import com.example.eshop.repositories.CategoryRepository;
import com.example.eshop.services.CategoryService;
import com.example.eshop.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

import static com.example.eshop.utils.PagesPathEnum.CATEGORY_PAGE;
import static com.example.eshop.utils.RequestParamsEnum.CATEGORY_PARAM;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductService productService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductService productService) {
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
}