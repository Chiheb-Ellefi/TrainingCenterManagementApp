package com.example.CenterManagement.controllers.users;

import com.example.CenterManagement.dto.user.EmployerDto;
import com.example.CenterManagement.services.users.EmployerService;
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
@RequestMapping("/api/v1/employers")
public class EmployerController {
    private final EmployerService employerService;

    @Autowired
    public EmployerController(EmployerService employerService) {
        this.employerService = employerService;
    }

    @Operation(
            summary = "Get all employers",
            description = "Retrieves a list of all employers."
    )
    @ApiResponse(responseCode = "200", description = "List of employers successfully retrieved",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = EmployerDto.class))))
    @GetMapping
    public ResponseEntity<List<EmployerDto>> getAllEmployers() {
        List<EmployerDto> response = employerService.getAllEmployers();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Create a new employer",
            description = "Creates a new employer with the provided data.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The necessary attributes to create a new employer",
                    content = @Content(schema = @Schema(implementation = EmployerDto.class))
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Employer created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployerDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid data was provided, check the request body",
                    content = @Content(mediaType = "text/plain"))
    })
    @PostMapping
    public ResponseEntity<EmployerDto> createEmployer(@RequestBody EmployerDto employerDto) {
        EmployerDto response = employerService.createEmployer(employerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Delete an employer by ID",
            description = "Deletes an employer based on the provided employer ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Employer successfully deleted",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "Provided employer ID is invalid",
                    content = @Content(mediaType = "text/plain"))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployer(@PathVariable long id) {
        employerService.deleteEmployer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}