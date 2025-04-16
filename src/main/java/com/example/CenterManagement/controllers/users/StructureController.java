package com.example.CenterManagement.controllers.users;

import com.example.CenterManagement.dto.user.StructureDto;
import com.example.CenterManagement.services.users.StructureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/structures")
public class StructureController {
    private final StructureService structureService;

    @Autowired
    public StructureController(StructureService structureService) {
        this.structureService = structureService;
    }

    @Operation(
            summary = "Get all structures",
            description = "Retrieves a list of all structures."
    )
    @ApiResponse(responseCode = "200", description = "List of structures successfully retrieved",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = StructureDto.class))))
    @GetMapping
    public ResponseEntity<List<StructureDto>> getAllStructures() {
        List<StructureDto> response = structureService.getAllStructures();
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Create a new structure",
            description = "Creates a new structure with the provided data.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The necessary attributes to create a new structure",
                    content = @Content(schema = @Schema(implementation = StructureDto.class))
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Structure created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StructureDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid data was provided, check the request body",
                    content = @Content(mediaType = "text/plain"))
    })
    @PostMapping
    public ResponseEntity<StructureDto> createStructure(@RequestBody StructureDto structureDto) {
        StructureDto response = structureService.createStructure(structureDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Delete a structure by ID",
            description = "Deletes a structure based on the provided structure ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Structure successfully deleted",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "Provided structure ID is invalid",
                    content = @Content(mediaType = "text/plain"))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStructure(@PathVariable long id) {
        structureService.deleteStructure(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}