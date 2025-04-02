package com.example.CenterManagement.repositories.users;

import com.example.CenterManagement.entities.user.Trainer;
import com.example.CenterManagement.entities.user.TrainerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    @Modifying
    @Query(value = "INSERT INTO trainers (user_id, trainer_type ,employer_name) VALUES(?1,?2,(SELECT employer_name FROM employers WHERE employer_name=?3))",nativeQuery = true)
        void createTrainer(Long userId, String trainerType,String employerName);
    @Modifying
    @Query(value = "UPDATE  trainers SET user_id=?1, trainer_type=?2 ,employer_name=(SELECT employer_name FROM employers WHERE employer_name=?3) WHERE trainer_id=?4 ",nativeQuery = true)
    void updateTrainer(Long userId, String trainerType,String employerName,Long trainerId);
}
