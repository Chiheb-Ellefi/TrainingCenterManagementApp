package com.example.CenterManagement.entities.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "structures")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Structure {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequenceName = "structure_sequence",name = "structure_seq", allocationSize = 1)
    private Long structureId;
    @Column(nullable = false,length = 100)
    private String structureName;
}
