package com.example.eshop.services;

import com.example.eshop.entities.User;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public interface UserService extends BaseServices<User> {
    ModelAndView authenticate(User user);

    ModelAndView addNewUser(User user);

    ModelAndView getProfileAccount(User user);
}
