package com.example.CenterManagement.entities.training;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "domains")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Domain {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "domain_seq", allocationSize = 1,sequenceName = "domain_sequence")
    private Long domainId;
    @Column(nullable = false,length = 100)
    private String domainName;
}
