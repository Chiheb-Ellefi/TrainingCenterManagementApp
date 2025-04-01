package com.example.CenterManagement.controllers;

import com.example.CenterManagement.services.users.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {
    private final ProfileService profileService;
    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }
}
