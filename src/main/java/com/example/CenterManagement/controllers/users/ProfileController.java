package com.example.CenterManagement.controllers.users;

import com.example.CenterManagement.dto.user.ProfileDto;
import com.example.CenterManagement.services.users.ProfileService;
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
@RequestMapping("/api/v1/profiles")
@SecurityRequirement(name = "bearerAuth")
public class ProfileController {
    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Operation(
            summary = "Get all profiles",
            description = "Retrieves a list of all profiles."
    )
    @ApiResponse(responseCode = "200", description = "List of profiles successfully retrieved",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ProfileDto.class))))
    @GetMapping
    public ResponseEntity<List<ProfileDto>> getAllProfiles() {
        List<ProfileDto> profiles = profileService.getAllProfiles();
        return new ResponseEntity<>(profiles, HttpStatus.OK);
    }

    @Operation(
            summary = "Create a new profile",
            description = "Creates a new profile with the provided data.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The necessary attributes to create a new profile",
                    content = @Content(schema = @Schema(implementation = ProfileDto.class))
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Profile created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid data was provided, check the request body",
                    content = @Content(mediaType = "text/plain"))
    })
    @PostMapping
    public ResponseEntity<ProfileDto> createProfile(@RequestBody ProfileDto profileDto) {
        ProfileDto createdProfile = profileService.createProfile(profileDto);
        return new ResponseEntity<>(createdProfile, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Delete a profile by ID",
            description = "Deletes a profile based on the provided profile ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Profile successfully deleted",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "Provided profile ID is invalid",
                    content = @Content(mediaType = "text/plain"))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProfile(@PathVariable Long id) {
        profileService.deleteProfileById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}