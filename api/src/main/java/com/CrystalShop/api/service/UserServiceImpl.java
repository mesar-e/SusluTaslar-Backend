package com.CrystalShop.api.service;

import com.CrystalShop.api.dto.UserRequest;
import com.CrystalShop.api.dto.UserResponse;
import com.CrystalShop.api.entity.User;
import com.CrystalShop.api.enums.Role;
import com.CrystalShop.api.exception.ApiException;
import com.CrystalShop.api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private void checkUserOwnershipOrAdmin(Long targetUserId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String loggedInEmail = ((UserDetails) principal).getUsername();

            User loggedInUser = userRepository.findByEmail(loggedInEmail)
                    .orElseThrow(() -> new ApiException("Giriş yapan kullanıcı bulunamadı!", HttpStatus.UNAUTHORIZED));

            if (loggedInUser.getRole() != Role.ADMIN && !loggedInUser.getId().equals(targetUserId)) {
                throw new ApiException("Erişim Reddedildi: Sadece kendi profilinize müdahale edebilirsiniz!", HttpStatus.FORBIDDEN);
            }
        } else {
            throw new ApiException("Sisteme giriş yapmanız gerekiyor!", HttpStatus.UNAUTHORIZED);
        }
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
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);

        UserResponse response = new UserResponse();
        response.setId(savedUser.getId());
        response.setFirstName(savedUser.getFirstName());
        response.setLastName(savedUser.getLastName());
        response.setEmail(savedUser.getEmail());
        response.setRole(savedUser.getRole());

        return response;
    }

    @Override
    public void delete(Long id) {

        checkUserOwnershipOrAdmin(id);

        User user = findById(id);

        userRepository.delete(user);
    }

    @Override
    public UserResponse update(Long id, UserRequest userRequest) {
        checkUserOwnershipOrAdmin(id);

        User existingUser = findById(id);

        existingUser.setFirstName(userRequest.getFirstName());
        existingUser.setLastName(userRequest.getLastName());
        existingUser.setEmail(userRequest.getEmail());
        existingUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        User updatedUser = userRepository.save(existingUser);

        UserResponse response = new UserResponse();

        response.setId(updatedUser.getId());
        response.setFirstName(updatedUser.getFirstName());
        response.setLastName(updatedUser.getLastName());
        response.setEmail(updatedUser.getEmail());
        response.setRole(updatedUser.getRole());

        return response;
    }

    @Override
    public UserResponse getUserDtoById(Long id) {

        checkUserOwnershipOrAdmin(id);

        User user = findById(id);

        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        return response;
    }

    @Override
    public List<UserResponse> getAllUsersDto() {

        List<User> users = findAll();

        return users.stream().map(user -> {
            UserResponse response = new UserResponse();
            response.setId(user.getId());
            response.setFirstName(user.getFirstName());
            response.setLastName(user.getLastName());
            response.setEmail(user.getEmail());
            response.setRole(user.getRole());
            return response;
        }).collect(Collectors.toList());
    }
}
