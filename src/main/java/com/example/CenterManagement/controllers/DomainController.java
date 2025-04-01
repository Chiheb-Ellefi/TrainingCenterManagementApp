package com.example.CenterManagement.controllers;

import com.example.CenterManagement.dto.user.ProfileDto;
import com.example.CenterManagement.dto.user.UserDto;
import com.example.CenterManagement.exceptions.BadRequestException;
import com.example.CenterManagement.services.users.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profiles")
public class DomainController {
    private final ProfileService profileService;
    @Autowired
    DomainController(ProfileService profileService) {
        this.profileService = profileService;
    }
    @PostMapping
    public ResponseEntity<ProfileDto> create(@RequestBody ProfileDto profileDto) {
        if(profileDto == null) {
            throw new BadRequestException("Invalid Data");
        }
       ProfileDto response= profileService.createProfile(profileDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<ProfileDto>> getAll() {
        List<ProfileDto> response= profileService.getAllProfiles();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{id}")
     public ResponseEntity<ProfileDto> getProfile(@PathVariable Long id) {
        if(id == null) {
            throw new BadRequestException("Invalid profile id");
        }
        ProfileDto response=profileService.getProfileById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        if(id == null) {
            throw new BadRequestException("Invalid profile id");
        }
        profileService.deleteProfileById(id);
        return new ResponseEntity<>("Profile deleted", HttpStatus.OK);
    }


}
