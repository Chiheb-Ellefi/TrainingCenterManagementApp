package com.example.CenterManagement.repositories.users;

import com.example.CenterManagement.entities.user.Structure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StructureRepository extends JpaRepository<Structure, Long> {
}
