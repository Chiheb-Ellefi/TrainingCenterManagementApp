package com.example.CenterManagement.services.training;

import com.example.CenterManagement.dto.training.DomainDto;
import com.example.CenterManagement.entities.training.Domain;
import com.example.CenterManagement.mappers.training.DomainMapper;
import com.example.CenterManagement.repositories.training.DomainRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DomainService {
    private final DomainRepository domainRepository;
    public DomainService(DomainRepository domainRepository) {
        this.domainRepository = domainRepository;
    }
    public List<DomainDto> getAllDomains() {
        List<Domain> domains = domainRepository.findAll();
        return domains.stream().map(DomainMapper::toDto).collect(Collectors.toList());
    }
    public DomainDto getDomain(Long id) {
        if(id == null) {
            throw new IllegalArgumentException("Domain id cannot be null");
        }
        return DomainMapper.toDto(domainRepository.findById(id).orElseThrow(()->new RuntimeException("Domain not found.")));
    }
    public DomainDto createDomain(DomainDto domainDto) {
        if(domainDto == null) {
            throw new IllegalArgumentException("Domain cannot be null");
        }
        Domain domain = DomainMapper.toEntity(domainDto);
        domain = domainRepository.save(domain);
        return DomainMapper.toDto(domain);
    }
    @Transactional
    public void deleteDomain(Long id) {
        if(id == null || !domainRepository.existsById(id)) {
            throw new IllegalArgumentException("Domain id cannot be null");
        }
        domainRepository.deleteById(id);
    }

}
