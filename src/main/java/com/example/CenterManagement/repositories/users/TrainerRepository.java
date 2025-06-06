package com.example.CenterManagement.repositories.users;

import com.example.CenterManagement.entities.user.Trainer;
import com.example.CenterManagement.entities.user.TrainerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    int countByTrainerType(TrainerType trainerType);
}
