package com.example.CenterManagement.repositories.training;

import com.example.CenterManagement.entities.training.Training;
import com.example.CenterManagement.entities.user.TrainerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, String> {
    @Query(nativeQuery = true,value="SELECT * FROM trainings WHERE trainer_id = ?1")
    List<Training> getTrainingsByTrainerId(Long id);

}