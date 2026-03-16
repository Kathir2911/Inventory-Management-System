package com.kathir.Inventory.Management.System.repository;

import com.kathir.Inventory.Management.System.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}