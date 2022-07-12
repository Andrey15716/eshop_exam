package com.example.eshop.repositories;

import com.example.eshop.entities.User;

public interface UserRepository extends BaseRepository<User> {
    User getUserByLoginAndPass(String login, String password);

    User addUser(User user);
}