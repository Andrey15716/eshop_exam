package com.example.eshop.services;

import com.example.eshop.entities.BaseEntity;
import com.example.eshop.exceptions.RepositoryExceptions;
import com.example.eshop.exceptions.ServiceExceptions;

import java.util.List;

public interface BaseServices<T extends BaseEntity> {
    T create(T entity) throws ServiceExceptions, RepositoryExceptions;

    List<T> read() throws ServiceExceptions, RepositoryExceptions;

    T update(T entity) throws ServiceExceptions, RepositoryExceptions;

    void delete(int id) throws ServiceExceptions, RepositoryExceptions;
}