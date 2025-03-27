package com.example.CenterManagement.dto.user;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class StructureDto {
    private Long structureId;
    private String structureName;
}