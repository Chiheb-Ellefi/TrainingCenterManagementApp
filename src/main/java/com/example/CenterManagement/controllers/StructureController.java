package com.example.CenterManagement.controllers;

import com.example.CenterManagement.services.users.ProfileService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StructureController {
    private final ProfileService profileService;
    public StructureController(ProfileService profileService) {
        this.profileService = profileService;
    }
}
