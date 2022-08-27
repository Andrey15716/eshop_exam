package com.example.eshop.repositories;

import com.example.eshop.entities.User;
import com.example.eshop.exceptions.RepositoryExceptions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User getUserByNameAndPassword(String name, String password) throws RepositoryExceptions;

    void deleteUserById(int id);
}