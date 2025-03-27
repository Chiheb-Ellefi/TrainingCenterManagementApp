package com.example.CenterManagement.repositories.users;

import com.example.CenterManagement.entities.user.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}
