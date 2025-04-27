package com.example.CenterManagement.repositories.training;

import com.example.CenterManagement.entities.training.Training;
import com.example.CenterManagement.entities.user.TrainerType;
import com.example.CenterManagement.models.dashboardData.DomainCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, String> {
    @Query(nativeQuery = true,value="SELECT * FROM trainings WHERE trainer_id = ?1")
    List<Training> getTrainingsByTrainerId(Long id);
    @Query(nativeQuery = true,value="SELECT domain_name domaine ,COUNT(*) nb FROM trainings GROUP BY domain_name ;")
    List<DomainCount>  getTrainingsPerDomain();

}
