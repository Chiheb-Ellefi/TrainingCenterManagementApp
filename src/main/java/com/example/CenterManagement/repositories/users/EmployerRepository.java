package com.example.CenterManagement.repositories.users;

import com.example.CenterManagement.entities.user.Employer;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {
    @Query(value = "SELECT COUNT(*)>0 FROM  employers e WHERE e.employer_name= ?1",nativeQuery = true)
    boolean existsBYEmployerName(String employerName);
}
