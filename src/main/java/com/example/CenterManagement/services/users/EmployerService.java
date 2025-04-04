package com.example.CenterManagement.services.users;

import com.example.CenterManagement.dto.user.EmployerDto;
import com.example.CenterManagement.exceptions.users.EmployerNotFoundException;
import com.example.CenterManagement.mappers.user.EmployerMapper;
import com.example.CenterManagement.repositories.users.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployerService {
    private final EmployerRepository employerRepository;
    @Autowired
    public EmployerService(EmployerRepository employerRepository) {
        this.employerRepository = employerRepository;
    }
    public List<EmployerDto> getAllEmployers() {
        return employerRepository.findAll().stream().map(EmployerMapper::toDto).collect(Collectors.toList());
    }

    public EmployerDto createEmployer(EmployerDto employerDto) {
        return EmployerMapper.toDto(employerRepository.save(EmployerMapper.toEntity(employerDto)));
    }
    public void deleteEmployer(long id) {
        if(!employerRepository.existsById(id)) {
            throw new EmployerNotFoundException("Employer with id " + id + " does not exist");
        }
        employerRepository.deleteById(id);
    }
}
