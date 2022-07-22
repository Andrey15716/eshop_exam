package com.example.eshop.repositories;

import com.example.eshop.entities.BaseEntity;

import java.util.List;

public interface BaseRepository<T extends BaseEntity> {
    T create(T entity);

    List<T> read();

    T update(T entity);

    void delete(int id);
}