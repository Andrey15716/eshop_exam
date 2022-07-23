package com.example.eshop.repositories;

import com.example.eshop.entities.User;
import com.example.eshop.exceptions.RepositoryExceptions;

public interface UserRepository extends BaseRepository<User> {
    User getUserByLoginAndPass(User user) throws RepositoryExceptions;

    User addUser(User user) throws RepositoryExceptions;
}