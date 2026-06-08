package com.CrystalShop.api.service;

import com.CrystalShop.api.dto.UserRequest;
import com.CrystalShop.api.dto.UserResponse;
import com.CrystalShop.api.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(Long id);
    UserResponse save(UserRequest userRequest);
    void delete(User user);
    UserResponse update(Long id, UserRequest userRequest);
    List<UserResponse> getAllUsersDto();
    UserResponse getUserDtoById(Long id);
}
