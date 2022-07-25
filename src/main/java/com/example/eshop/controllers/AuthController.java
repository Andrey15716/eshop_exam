package com.example.eshop.controllers;

import com.example.eshop.entities.User;
import com.example.eshop.exceptions.AuthorizationsExceptions;
import com.example.eshop.exceptions.RepositoryExceptions;
import com.example.eshop.exceptions.ServiceExceptions;
import com.example.eshop.services.UserService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Objects;

import static com.example.eshop.utils.EshopConstants.NAME;
import static com.example.eshop.utils.EshopConstants.PASSWORD;
import static com.example.eshop.utils.EshopConstants.USER;
import static com.example.eshop.utils.PagesPathEnum.SIGN_IN_PAGE;
import static com.example.eshop.utils.PagesPathEnum.START_PAGE;

@RestController
@SessionAttributes({USER})
@RequestMapping("/login")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView openLoginPage() {
        return new ModelAndView(SIGN_IN_PAGE.getPath());
    }

    @PostMapping
    public ModelAndView authenticate(@ModelAttribute(USER) @Valid User user, BindingResult bindingResult, ModelAndView modelAndView) throws AuthorizationsExceptions, ServiceExceptions, RepositoryExceptions {
        if (bindingResult.hasErrors()) {
            fieldError(NAME, modelAndView, bindingResult);
            fieldError(PASSWORD, modelAndView, bindingResult);
            modelAndView.setViewName(START_PAGE.getPath());
            return modelAndView;
        }
        return userService.authenticate(user);
    }

    @GetMapping("/profile")
    public ModelAndView getProfilePage(@ModelAttribute(USER) User user) throws ServiceExceptions, RepositoryExceptions {
        return userService.getProfileAccount(user);
    }

    @ModelAttribute(USER)
    public User setUpUser() {
        return new User();
    }

    private void fieldError(String field, ModelAndView modelAndView, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors(field)) {
            modelAndView.addObject(field + "Error", Objects.requireNonNull(bindingResult.getFieldError(field))
                    .getDefaultMessage());
        }
    }
}
