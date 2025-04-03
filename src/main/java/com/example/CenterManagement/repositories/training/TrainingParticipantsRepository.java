package com.example.CenterManagement.repositories.training;
import com.example.CenterManagement.entities.training.TrainingParticipant;
import com.example.CenterManagement.entities.training.TrainingParticipantsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingParticipantsRepository extends JpaRepository<TrainingParticipant, TrainingParticipantsId> {

    @Query(nativeQuery = true,value = "SELECT * FROM training_participants WHERE training_id=?1")
    List<TrainingParticipant> findAllByTrainingId(String trainingId);
    @Query(nativeQuery = true,value = "SELECT * FROM training_participants WHERE participant_id=?1")
    List<TrainingParticipant> findTrainingsForParticipant(Long userId);
}
