package com.CrystalShop.api.service;

import com.CrystalShop.api.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(Long id);
    User save(User user);
    void delete(User user);
}
