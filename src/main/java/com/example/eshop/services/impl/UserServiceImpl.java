package com.example.eshop.services.impl;

import com.example.eshop.entities.Category;
import com.example.eshop.entities.Order;
import com.example.eshop.entities.User;
import com.example.eshop.exceptions.AuthorizationsExceptions;
import com.example.eshop.exceptions.RepositoryExceptions;
import com.example.eshop.exceptions.ServiceExceptions;
import com.example.eshop.repositories.OrderRepository;
import com.example.eshop.repositories.UserRepository;
import com.example.eshop.services.CategoryService;
import com.example.eshop.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.eshop.utils.PagesPathEnum.PROFILE_PAGE;
import static com.example.eshop.utils.PagesPathEnum.REGISTRATION_SUCCESS_PAGE;
import static com.example.eshop.utils.PagesPathEnum.START_PAGE;
import static com.example.eshop.utils.RequestParamsEnum.CATEGORIES_PARAM;
import static com.example.eshop.utils.RequestParamsEnum.LOGGED_IN_USER_PARAM;
import static com.example.eshop.utils.RequestParamsEnum.LOGIN_PARAM;
import static com.example.eshop.utils.RequestParamsEnum.PAGE_NUMBER_PARAM;
import static com.example.eshop.utils.RequestParamsEnum.USER_ORDERS_PARAM;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final OrderRepository orderRepository;

    public UserServiceImpl(UserRepository userRepository, CategoryService categoryService, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.categoryService = categoryService;
        this.orderRepository = orderRepository;
    }

    @Override
    public User create(User entity) throws ServiceExceptions, RepositoryExceptions {
        return userRepository.addUser(entity);
    }

    @Override
    public List<User> read() throws ServiceExceptions, RepositoryExceptions {
        return userRepository.read();
    }

    @Override
    public User update(User entity) throws ServiceExceptions, RepositoryExceptions {
        return userRepository.update(entity);
    }

    @Override
    public void delete(int id) throws ServiceExceptions, RepositoryExceptions {
        userRepository.delete(id);
    }

    @Override
    public ModelAndView authenticate(User user) throws ServiceExceptions, RepositoryExceptions, AuthorizationsExceptions {
        ModelAndView modelAndView = new ModelAndView();
        if (Optional.ofNullable(user).isPresent()
                && Optional.ofNullable(user.getName()).isPresent()
                && Optional.ofNullable(user.getPassword()).isPresent()) {
            User loggedUser = userRepository.getUserByLoginAndPass(user);
            if (Optional.ofNullable(loggedUser).isPresent()) {
                ModelMap modelMap = new ModelMap();
                List<Category> categoriesList = categoryService.read();
                modelMap.addAttribute(CATEGORIES_PARAM.getValue(), categoriesList);
                modelAndView.setViewName(START_PAGE.getPath());
                modelAndView.addAllObjects(modelMap);
                log.info("User is authenticated!");
            } else {
                log.info("User is not found!");
                throw new AuthorizationsExceptions("User is not authorised!");
            }
        }
        return modelAndView;
    }

    @Override
    public ModelAndView addNewUser(User user) throws ServiceExceptions, RepositoryExceptions {
        ModelAndView modelAndView = new ModelAndView();
        ModelMap modelMap = new ModelMap();
        String username = user.getName();
        modelMap.addAttribute(LOGIN_PARAM.getValue(), username);
        modelAndView.addObject(modelMap);
        create(user);
        modelAndView.setViewName(REGISTRATION_SUCCESS_PAGE.getPath());
        log.info("New user has been added!");
        return modelAndView;
    }

    @Override
    public ModelAndView getProfileAccount(User user) throws ServiceExceptions, RepositoryExceptions {
        ModelAndView modelAndView = new ModelAndView();
        User loggedInUser = userRepository.getUserByLoginAndPass(user);
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(LOGGED_IN_USER_PARAM.getValue(), loggedInUser);
        int userId = loggedInUser.getId();
        user.setId(userId);
        List<Order> userOrders = orderRepository.getAllOrdersByUserId(userId);
        modelMap.addAttribute(USER_ORDERS_PARAM.getValue(), userOrders);
        modelAndView.setViewName(PROFILE_PAGE.getPath());
        modelAndView.addAllObjects(modelMap);
        log.info("User got your profile account");
        return modelAndView;
    }

    @Override
    public ModelAndView getProfileAccountPagination(User user, int number) throws ServiceExceptions, RepositoryExceptions {
        User loggedInUser = userRepository.getUserByLoginAndPass(user);
        ModelAndView modelAndView = new ModelAndView();
        if (Optional.ofNullable(loggedInUser).isPresent()) {
            ModelMap modelMap = new ModelMap();
            modelMap.addAttribute(LOGGED_IN_USER_PARAM.getValue(), loggedInUser);
            int userId = loggedInUser.getId();
            user.setId(userId);
            List<Order> userOrders = orderRepository.getAllOrdersByUserIdPagination(userId, number);
            long numberPages = orderRepository.getNumberOfOrdersPerPage(userId);
            List<Long> listPages = new ArrayList<>();
            for (long i = 1; i <= numberPages; i++) {
                listPages.add(i);
            }
            modelMap.addAttribute(PAGE_NUMBER_PARAM.getValue(), listPages);
            modelMap.addAttribute(USER_ORDERS_PARAM.getValue(), userOrders);
            modelAndView.setViewName(PROFILE_PAGE.getPath());
            modelAndView.addAllObjects(modelMap);
            log.info("User got profile account pagination");
        }
        return modelAndView;
    }
}