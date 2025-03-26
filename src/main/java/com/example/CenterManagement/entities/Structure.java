package com.example.CenterManagement.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "structures")
public class Structure {
    @Id
    private Long structureId;
    @Column(nullable = false,length = 100)
    private String structureName;
}
