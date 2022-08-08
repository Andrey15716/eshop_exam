package com.example.eshop.repositories;

import com.example.eshop.entities.BaseEntity;
import com.example.eshop.exceptions.RepositoryExceptions;

import java.util.List;

public interface BaseRepository<T extends BaseEntity> {
    T create(T entity) throws RepositoryExceptions;

    List<T> read() throws RepositoryExceptions;

    T update(T entity) throws RepositoryExceptions;

    void delete(int id) throws RepositoryExceptions;
}