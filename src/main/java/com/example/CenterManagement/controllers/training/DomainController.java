package com.example.CenterManagement.controllers.training;

import com.example.CenterManagement.dto.training.DomainDto;
import com.example.CenterManagement.exceptions.BadRequestException;
import com.example.CenterManagement.services.training.DomainService;
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
@RequestMapping("/api/v1/domains")
public class DomainController {
    private final DomainService domainService;

    @Autowired
    DomainController(DomainService domainService) {
        this.domainService = domainService;
    }

    @Operation(
            summary = "Create a new domain",
            description = "Creates a new domain with the provided data.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The necessary attributes to create a new domain",
                    content = @Content(schema = @Schema(implementation = DomainDto.class))
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Domain created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DomainDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid data was provided, check the request body",
                    content = @Content(mediaType = "text/plain"))
    })
    @PostMapping
    public ResponseEntity<DomainDto> createDomain(@RequestBody DomainDto domainDto) {
        if (domainDto == null) {
            throw new BadRequestException("Invalid Data");
        }
        DomainDto response = domainService.createDomain(domainDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all domains",
            description = "Retrieves a list of all domains."
    )
    @ApiResponse(responseCode = "200", description = "List of domains successfully retrieved",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = DomainDto.class))))
    @GetMapping
    public ResponseEntity<List<DomainDto>> getAllDomains() {
        List<DomainDto> response = domainService.getAllDomains();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Get a domain by ID",
            description = "Fetches a domain using its unique ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Domain found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DomainDto.class))),
            @ApiResponse(responseCode = "400", description = "Provided domain ID is null",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "Domain not found",
                    content = @Content(mediaType = "text/plain"))
    })
    @GetMapping("/{id}")
    public ResponseEntity<DomainDto> getDomain(@PathVariable Long id) {
        if (id == null) {
            throw new BadRequestException("Invalid domain id");
        }
        DomainDto response = domainService.getDomain(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete a domain by ID",
            description = "Deletes a domain based on the provided domain ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Domain successfully deleted",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "Provided domain ID is invalid",
                    content = @Content(mediaType = "text/plain"))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDomain(@PathVariable Long id) {
        if (id == null) {
            throw new BadRequestException("Invalid domain id");
        }
        domainService.deleteDomain(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}