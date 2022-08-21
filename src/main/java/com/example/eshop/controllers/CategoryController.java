package com.example.eshop.controllers;

import com.example.eshop.exceptions.RepositoryExceptions;
import com.example.eshop.exceptions.ServiceExceptions;
import com.example.eshop.services.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


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

    @GetMapping("/download")
    public void downloadCategoryCsv(HttpServletResponse response) throws IOException, RepositoryExceptions, ServiceExceptions {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF8");
        response.addHeader("Content-Disposition", "attachment; filename=category.csv");
        categoryService.downloadCsvFile(response.getWriter());
    }

    @PostMapping("/upload")
    public void uploadCategoryCsv(@RequestParam("file") MultipartFile file) throws IOException {
        categoryService.saveCategoriesFromCsvFile(file.getInputStream());
    }
}