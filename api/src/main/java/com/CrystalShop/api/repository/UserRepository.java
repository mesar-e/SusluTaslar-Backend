package com.CrystalShop.api.repository;

import com.CrystalShop.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
