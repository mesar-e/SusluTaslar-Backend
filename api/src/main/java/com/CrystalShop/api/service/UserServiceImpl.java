package com.CrystalShop.api.service;

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
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }
}
