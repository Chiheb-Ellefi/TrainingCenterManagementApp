package com.example.CenterManagement.repositories.training;

import com.example.CenterManagement.entities.training.TrainingParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingParticipantsRepository extends JpaRepository<TrainingParticipant, Long> {
}
