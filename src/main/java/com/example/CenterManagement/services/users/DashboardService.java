package com.example.CenterManagement.services.users;

import com.example.CenterManagement.entities.user.Gender;
import com.example.CenterManagement.entities.user.TrainerType;
import com.example.CenterManagement.models.OtherDetails;
import com.example.CenterManagement.models.dashboardData.ParticipantsDetails;
import com.example.CenterManagement.models.dashboardData.TrainersDetails;
import com.example.CenterManagement.models.dashboardData.TrainingsDetails;
import com.example.CenterManagement.repositories.training.TrainingParticipantsRepository;
import com.example.CenterManagement.repositories.training.TrainingRepository;
import com.example.CenterManagement.repositories.users.ParticipantRepository;
import com.example.CenterManagement.repositories.users.TrainerRepository;
import com.example.CenterManagement.repositories.users.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Transactional
@Service
public class DashboardService {
  private final   ParticipantRepository participantRepository;
  private final TrainingRepository trainingRepository;
  private final TrainerRepository trainerRepository;
 private final TrainingParticipantsRepository trainingParticipantsRepository;
 private final UserRepository userRepository;
  @Autowired
  public DashboardService(ParticipantRepository participantRepository, TrainingRepository trainingRepository, TrainerRepository trainerRepository,
                          TrainingParticipantsRepository trainingParticipantsRepository, UserRepository userRepository) {
      this.participantRepository = participantRepository;
      this.trainingRepository = trainingRepository;
      this.trainerRepository = trainerRepository;
      this.trainingParticipantsRepository = trainingParticipantsRepository;
      this.userRepository = userRepository;
  }

  public OtherDetails getOtherDetails() {
      int nbOfFemale =userRepository.countByGender(Gender.FEMALE);
      int nbOfMale =userRepository.countByGender(Gender.MALE);
return OtherDetails.builder().nbOfMale(nbOfMale).nbOfFemale(nbOfFemale).build();
  }
public TrainingsDetails getTrainingsDetails(){
    long nbTrainings=trainingRepository.count();
    AtomicReference<Long> income= new AtomicReference<>(0L);
    trainingParticipantsRepository.getAllTrainingIds().forEach(trainingId -> {
        income.set(income.get() + trainingParticipantsRepository.getIncomePerTraining(trainingId));
    });
   return TrainingsDetails.builder().nbTrainings(nbTrainings).totalIncome(income.get()).build();

}
public TrainersDetails getTrainersDetails(){
    long nbTrainers=trainerRepository.count();
     int internalTrainersCount= trainerRepository.countByTrainerType(TrainerType.INTERNAL);
     int externalTrainersCount= trainerRepository.countByTrainerType(TrainerType.EXTERNAL);
     HashMap<String, Long> topTrainers = null;
     return TrainersDetails.builder()
                     .externalTrainersCount(externalTrainersCount).internalTrainersCount(internalTrainersCount)
             .topTrainers(topTrainers).nbTrainers(nbTrainers).build();
}
public ParticipantsDetails getParticipantsDetails(){
    long nbParticipants=participantRepository.count();
    return ParticipantsDetails.builder().nbParticipants(nbParticipants).build();
}

}
