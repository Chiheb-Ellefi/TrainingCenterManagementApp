package com.example.CenterManagement.mappers.user;

import com.example.CenterManagement.dto.user.StructureDto;
import com.example.CenterManagement.entities.user.Structure;

public class StructureMapper {
    static public Structure toEntity(StructureDto structureDto) {
        return Structure.builder()
                .structureId(structureDto.getStructureId())
                .structureName(structureDto.getStructureName())
                .build();
    }
    public static StructureDto toDto(Structure structure) {
        return StructureDto.builder()
                .structureId(structure.getStructureId())
                .structureName(structure.getStructureName())
                .build();
    }
}
