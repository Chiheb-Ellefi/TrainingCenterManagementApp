package com.example.CenterManagement.services.users;

import com.example.CenterManagement.dto.user.StructureDto;
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
    public StructureDto getStructure(Long id) {
        if(id == null) {
            throw new IllegalArgumentException("Structure id cannot be null");
        }
        return StructureMapper.toDto(structureRepository.findById(id).orElseThrow(()->new RuntimeException("Structure Not Found")));
    }
    public List<StructureDto> getAllStructures() {
        return structureRepository.findAll().stream().map(StructureMapper::toDto).collect(Collectors.toList());
    }
    public StructureDto createStructure(StructureDto structureDto) {
        if(structureDto == null) {
            throw new IllegalArgumentException("Structure dto cannot be null");
        }
        return StructureMapper.toDto(structureRepository.save(StructureMapper.toEntity(structureDto)));
    }
    @Transactional
    public void deleteStructure(Long id) {
        if( !structureRepository.existsById(id)) {
            throw new IllegalArgumentException("Structure Not Found");
        }
        structureRepository.deleteById(id);
    }
}
