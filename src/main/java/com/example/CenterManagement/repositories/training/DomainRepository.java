package com.example.CenterManagement.repositories.training;

import com.example.CenterManagement.entities.training.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomainRepository extends JpaRepository<Domain, Long> {
}
