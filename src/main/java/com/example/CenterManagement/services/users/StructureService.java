package com.example.CenterManagement.services.users;

import com.example.CenterManagement.dto.user.StructureDto;
import com.example.CenterManagement.exceptions.users.StructureNotFoundException;
import com.example.CenterManagement.mappers.user.StructureMapper;
import com.example.CenterManagement.repositories.users.StructureRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StructureService {
    private final StructureRepository structureRepository;
    public StructureService(StructureRepository structureRepository) {
        this.structureRepository = structureRepository;
    }
    public List<StructureDto> getAllStructures() {
        return structureRepository.findAll().stream().map(StructureMapper::toDto).collect(Collectors.toList());
    }
    public StructureDto createStructure(StructureDto structureDto) {
        return StructureMapper.toDto(structureRepository.save(StructureMapper.toEntity(structureDto)));
    }
    @Transactional
    public void deleteStructure(Long id) {
        if( !structureRepository.existsById(id)) {
            throw new StructureNotFoundException("Structure with id: "+id+" does not exist");
        }
        structureRepository.deleteById(id);
    }
}
