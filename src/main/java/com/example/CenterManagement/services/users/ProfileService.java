package com.example.CenterManagement.services.users;

import com.example.CenterManagement.dto.user.ProfileDto;
import com.example.CenterManagement.mappers.user.ProfileMapper;
import com.example.CenterManagement.repositories.users.ProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileService {
    final private ProfileRepository profileRepository;
    ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public List<ProfileDto> getAllProfiles() {
        return profileRepository.findAll().stream().map(ProfileMapper::toDto).collect(Collectors.toList());
    }
    public ProfileDto getProfileById(Long id) {
        return ProfileMapper.toDto(profileRepository.findById(id).orElseThrow(()-> new RuntimeException("Profile not found")));
    }
    public ProfileDto createProfile(ProfileDto profileDto) {
        return ProfileMapper.toDto(profileRepository.save(ProfileMapper.toEntity(profileDto)));
    }
    @Transactional
    public void deleteProfileById(Long id) {
        if(id==null || !profileRepository.existsById(id)) {
           throw  new IllegalArgumentException("Invalid profile id");
        }
        profileRepository.deleteById(id);
    }

}
