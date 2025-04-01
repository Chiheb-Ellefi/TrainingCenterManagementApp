package com.example.CenterManagement.repositories.training;

import com.example.CenterManagement.entities.training.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepository extends JpaRepository<Training, String> {
}
