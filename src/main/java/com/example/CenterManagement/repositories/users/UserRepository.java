package com.example.CenterManagement.repositories.users;

import com.example.CenterManagement.entities.user.Gender;
import com.example.CenterManagement.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    int countByGender(Gender gender);
    Optional<User> findByEmail(String email);
}
