package com.example.CenterManagement.repositories.users;

import com.example.CenterManagement.entities.user.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {
}
