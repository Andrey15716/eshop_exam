package com.example.eshop.services;

import com.example.eshop.entities.User;
import com.example.eshop.exceptions.AuthorizationsExceptions;
import com.example.eshop.exceptions.RepositoryExceptions;
import com.example.eshop.exceptions.ServiceExceptions;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.io.Writer;

@Service
public interface UserService extends BaseServices<User> {
    ModelAndView authenticate(User user) throws ServiceExceptions, RepositoryExceptions, AuthorizationsExceptions;

    ModelAndView addNewUser(User user) throws ServiceExceptions, RepositoryExceptions;

    ModelAndView getProfileAccount(User user) throws ServiceExceptions, RepositoryExceptions;

    ModelAndView getProfileAccountPagination(User user, int number) throws ServiceExceptions, RepositoryExceptions;

    void downloadCsvFile(Writer writer) throws RepositoryExceptions, ServiceExceptions;

    void downloadOrderCsvFile(Writer writer, int id) throws RepositoryExceptions, ServiceExceptions;

}