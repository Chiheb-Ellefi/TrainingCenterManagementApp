package com.example.CenterManagement.services.users;

import com.example.CenterManagement.dto.user.ProfileDto;
import com.example.CenterManagement.exceptions.users.ProfileNotFoundException;
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

    public ProfileDto createProfile(ProfileDto profileDto) {
        return ProfileMapper.toDto(profileRepository.save(ProfileMapper.toEntity(profileDto)));
    }
    @Transactional
    public void deleteProfileById(Long id) {
        if( !profileRepository.existsById(id)) {
           throw  new ProfileNotFoundException("Profile with id "+id+" does not exist");
        }
        profileRepository.deleteById(id);
    }

}
