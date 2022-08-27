package com.example.eshop.services;

import com.example.eshop.entities.Cart;
import com.example.eshop.entities.Order;
import com.example.eshop.entities.Product;
import com.example.eshop.entities.User;
import com.example.eshop.exceptions.RepositoryExceptions;
import com.example.eshop.repositories.OrderRepository;
import com.example.eshop.repositories.ProductRepository;
import com.example.eshop.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

import static com.example.eshop.utils.PagesPathEnum.CART_PAGE;
import static com.example.eshop.utils.PagesPathEnum.ORDER_PAGE;
import static com.example.eshop.utils.RequestParamsEnum.ORDER_ID_PARAM;
import static com.example.eshop.utils.RequestParamsEnum.PRICE_ORDER_PARAM;
import static com.example.eshop.utils.RequestParamsEnum.PRODUCT_PARAM;
import static com.example.eshop.utils.RequestParamsEnum.SHOPPING_CART_PARAM;

@Service
public class CartService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public CartService(ProductRepository productRepository, OrderRepository orderRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public ModelAndView addProductToCart(int productId, Cart shopCart) throws RepositoryExceptions {
        ModelMap modelParams = new ModelMap();
        Product product = productRepository.getProductById(productId);
        shopCart.addProduct(product);
        modelParams.addAttribute(PRODUCT_PARAM.getValue(), product);
        modelParams.addAttribute(SHOPPING_CART_PARAM.getValue(), shopCart);
        return new ModelAndView(CART_PAGE.getPath(), modelParams);
    }

    public ModelAndView buyProduct(Cart shopCart, User user) throws RepositoryExceptions {
        ModelMap modelMap = new ModelMap();
        List<Product> products = shopCart.getProducts();
        int priceOrder = shopCart.getTotalPrice();
        LocalDate date = LocalDate.now();
        User loggedInUser = userRepository.getUserByNameAndPassword(user.getName(), user.getPassword());
        int userId = loggedInUser.getId();
        user.setId(userId);
        Order order = Order.builder().priceOrder(priceOrder).
                date(date).
                user(user).
                productList(products).
                build();
        Order createdOrder = orderRepository.save(order);
        modelMap.addAttribute(PRICE_ORDER_PARAM.getValue(), shopCart.getTotalPrice());
        modelMap.addAttribute(ORDER_ID_PARAM.getValue(), createdOrder.getId());
        shopCart.clearCart();
        shopCart.setTotalPrice(0);
        return new ModelAndView(ORDER_PAGE.getPath(), modelMap);
    }
}