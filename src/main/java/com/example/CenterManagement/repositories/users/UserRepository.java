package com.example.CenterManagement.repositories.users;

import com.example.CenterManagement.entities.user.Gender;
import com.example.CenterManagement.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    int countByGender(Gender gender);
    User findByEmail( String email);
}
