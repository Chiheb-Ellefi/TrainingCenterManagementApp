package com.example.CenterManagement.repositories.training;

import com.example.CenterManagement.entities.training.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DomainRepository extends JpaRepository<Domain, Long> {
    @Query(value = "SELECT domain_name FROM domains WHERE domain_name=?1", nativeQuery = true)
    String getDomainByDomainName(String domainName);
}
