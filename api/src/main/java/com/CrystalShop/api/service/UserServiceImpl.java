package com.CrystalShop.api.service;

import com.CrystalShop.api.dto.UserRequest;
import com.CrystalShop.api.dto.UserResponse;
import com.CrystalShop.api.entity.User;
import com.CrystalShop.api.exception.ApiException;
import com.CrystalShop.api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new ApiException("user id is not present "+ id, HttpStatus.NOT_FOUND);
    }

    @Override
    public UserResponse save(UserRequest userRequest) {

        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName((userRequest.getLastName()));
        user.setEmail(userRequest.getEmail());
        user.setPassword(user.getPassword());

        User savedUser = userRepository.save(user);

        UserResponse response = new UserResponse();
        response.setId(savedUser.getId());
        response.setFirstName(savedUser.getFirstName());
        response.setLastName(savedUser.getLastName());
        response.setEmail(savedUser.getEmail());

        return response;
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public UserResponse update(Long id, UserRequest userRequest) {
        User existingUser = findById(id);

        existingUser.setFirstName(userRequest.getFirstName());
        existingUser.setLastName(userRequest.getLastName());
        existingUser.setEmail(userRequest.getEmail());
        existingUser.setPassword(userRequest.getPassword());

        User updatedUser = userRepository.save(existingUser);

        UserResponse response = new UserResponse();

        response.setId(updatedUser.getId());
        response.setFirstName(updatedUser.getFirstName());
        response.setLastName(updatedUser.getLastName());
        response.setEmail(updatedUser.getEmail());

        return response;
    }
}
