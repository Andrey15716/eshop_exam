package com.example.eshop.controllers;

import com.example.eshop.entities.User;
import com.example.eshop.exceptions.RegistrationExceptions;
import com.example.eshop.exceptions.RepositoryExceptions;
import com.example.eshop.exceptions.ServiceExceptions;
import com.example.eshop.services.UserService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import java.util.Objects;

import static com.example.eshop.utils.EshopConstants.ERROR;
import static com.example.eshop.utils.EshopConstants.NAME;
import static com.example.eshop.utils.EshopConstants.PASSWORD;
import static com.example.eshop.utils.EshopConstants.USER;
import static com.example.eshop.utils.PagesPathEnum.REGISTRATION_PAGE;

@RestController
@RequestMapping("/registration")
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView openRegistrationPage() {
        return new ModelAndView(REGISTRATION_PAGE.getPath());
    }

    @PostMapping
    public ModelAndView login(@ModelAttribute(USER) @Valid User user, BindingResult bindingResult, ModelAndView modelAndView) throws RegistrationExceptions, ServiceExceptions, RepositoryExceptions {
        if (bindingResult.hasErrors()) {
            fieldError(NAME, modelAndView, bindingResult);
            fieldError(PASSWORD, modelAndView, bindingResult);
            modelAndView.setViewName(REGISTRATION_PAGE.getPath());
            return modelAndView;
        }
        return userService.addNewUser(user);
    }

    @ModelAttribute(USER)
    public User setUpUserForm() {
        return new User();
    }

    private void fieldError(String field, ModelAndView modelAndView, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors(field)) {
            modelAndView.addObject(field + ERROR, Objects.requireNonNull(bindingResult.getFieldError(field))
                    .getDefaultMessage());
        }
    }
}