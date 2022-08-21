package com.example.eshop.controllers;

import com.example.eshop.exceptions.RepositoryExceptions;
import com.example.eshop.exceptions.ServiceExceptions;
import com.example.eshop.services.ProductService;
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
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ModelAndView openProductPage(@PathVariable int id) throws ServiceExceptions, RepositoryExceptions {
        return productService.getProductById(id);
    }

    @GetMapping("/download")
    public void downloadProductsCsv(HttpServletResponse response) throws IOException, RepositoryExceptions, ServiceExceptions {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF8");
        response.addHeader("Content-Disposition", "attachment; filename=category.csv");
        productService.downloadCsvFile(response.getWriter());
    }

    @PostMapping("/upload")
    public void uploadProductsCsv(@RequestParam("file") MultipartFile file) throws IOException {
        productService.saveProductsFromCsvFile(file.getInputStream());
    }
}