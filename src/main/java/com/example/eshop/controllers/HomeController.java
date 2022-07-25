package com.example.eshop.controllers;

import com.example.eshop.entities.Category;
import com.example.eshop.entities.User;
import com.example.eshop.exceptions.RepositoryExceptions;
import com.example.eshop.exceptions.ServiceExceptions;
import com.example.eshop.services.CategoryService;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.example.eshop.utils.EshopConstants.USER;
import static com.example.eshop.utils.PagesPathEnum.START_PAGE;
import static com.example.eshop.utils.RequestParamsEnum.CATEGORIES_PARAM;

@RestController
@RequestMapping("/home")
public class HomeController {
    private final CategoryService categoryService;

    public HomeController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    public ModelAndView getHomePage(@SessionAttribute(USER) User user) throws ServiceExceptions, RepositoryExceptions {
        ModelMap model = new ModelMap();
        List<Category> categoriesList = categoryService.read();
        model.addAttribute(CATEGORIES_PARAM.getValue(), categoriesList);
        return new ModelAndView(START_PAGE.getPath(), model);
    }
}