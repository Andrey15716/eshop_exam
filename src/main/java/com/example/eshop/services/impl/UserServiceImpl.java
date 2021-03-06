package com.example.eshop.services.impl;

import com.example.eshop.entities.Category;
import com.example.eshop.entities.Order;
import com.example.eshop.entities.Product;
import com.example.eshop.entities.User;
import com.example.eshop.repositories.OrderRepository;
import com.example.eshop.repositories.ProductRepository;
import com.example.eshop.repositories.UserRepository;
import com.example.eshop.services.CategoryService;
import com.example.eshop.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static com.example.eshop.utils.PagesPathEnum.PROFILE_PAGE;
import static com.example.eshop.utils.PagesPathEnum.REGISTRATION_SUCCESS_PAGE;
import static com.example.eshop.utils.PagesPathEnum.SIGN_IN_PAGE;
import static com.example.eshop.utils.PagesPathEnum.START_PAGE;
import static com.example.eshop.utils.RequestParamsEnum.CATEGORIES_PARAM;
import static com.example.eshop.utils.RequestParamsEnum.LOGGED_IN_USER_PARAM;
import static com.example.eshop.utils.RequestParamsEnum.LOGIN_PARAM;
import static com.example.eshop.utils.RequestParamsEnum.USER_ORDERS_PARAM;

@Slf4j
@Repository
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final OrderRepository orderRepository;

    public UserServiceImpl(UserRepository userRepository, ProductRepository productRepository, CategoryService categoryService, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.orderRepository = orderRepository;
    }

    @Override
    public User create(User entity) {
        return userRepository.addUser(entity);
    }

    @Override
    public List<User> read() {
        return userRepository.read();
    }

    @Override
    public User update(User entity) {
        return userRepository.update(entity);
    }

    @Override
    public void delete(int id) {
        userRepository.delete(id);
    }

    @Override
    public ModelAndView authenticate(User user) {
        ModelAndView modelAndView = new ModelAndView();
        if (Optional.ofNullable(user).isPresent()
                && Optional.ofNullable(user.getName()).isPresent()
                && Optional.ofNullable(user.getPassword()).isPresent()) {
            User loggedUser = userRepository.getUserByLoginAndPass(user.getName(), user.getPassword());
            if (Optional.ofNullable(loggedUser).isPresent()) {
                ModelMap modelMap = new ModelMap();
                List<Category> categoriesList = categoryService.read();
                modelMap.addAttribute(CATEGORIES_PARAM.getValue(), categoriesList);
                modelAndView.setViewName(START_PAGE.getPath());
                modelAndView.addAllObjects(modelMap);
                log.info("User is authenticated!");
            } else {
                modelAndView.setViewName(SIGN_IN_PAGE.getPath());
                log.info("User is not found!");
            }
        }
        return modelAndView;
    }

    @Override
    public ModelAndView addNewUser(User user) {
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
    public ModelAndView getProfileAccount(User user) {
        ModelAndView modelAndView = new ModelAndView();
        User loggedInUser = userRepository.getUserByLoginAndPass(user.getName(), user.getPassword());
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(LOGGED_IN_USER_PARAM.getValue(), loggedInUser);
        int userId = loggedInUser.getId();
        user.setId(userId);
        List<Order> userOrders = new ArrayList<>();
        List<Integer> ordersIds = orderRepository.getAllOrdersIdsByUserId(userId);
        for (Integer ordersId : ordersIds) {
            List<Product> orderProducts = productRepository.getAllProductsByOrderId(ordersId);
            Order order = orderRepository.getOrderById(ordersId);
            order.setProductList(orderProducts);
            userOrders.add(order);
        }
        modelMap.addAttribute(USER_ORDERS_PARAM.getValue(), userOrders);
        modelAndView.setViewName(PROFILE_PAGE.getPath());
        modelAndView.addAllObjects(modelMap);
        log.info("User got your profile account");
        return modelAndView;
    }
}