package com.example.CenterManagement.services.users;

import com.example.CenterManagement.dto.user.UserDto;
import com.example.CenterManagement.exceptions.users.UserNotFoundException;
import com.example.CenterManagement.mappers.user.UserMapper;
import com.example.CenterManagement.repositories.users.UserRepository;
import com.example.CenterManagement.repositories.users.redisRepositories.UserCacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserCacheRepository userCacheRepository;
    @Value("${spring.application.offset}")
     private int offset ;
    @Autowired
    public UserService(UserRepository userRepository, UserCacheRepository userCacheRepository) {
        this.userRepository = userRepository;
        this.userCacheRepository = userCacheRepository;
    }

    public List<UserDto> getAllUsers(int page) {
        return userRepository.findAll(PageRequest.of(page,offset ))
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
    }
    @Transactional
    public UserDto createUser(UserDto userDto) {
        return UserMapper.toDto(userRepository.save(UserMapper.toEntity(userDto)));
    }

    public UserDto updateUser(UserDto userDto) {
        return UserMapper.toDto(userRepository.save(UserMapper.toEntity(userDto)));
    }

    public void deleteUser(Long id) {
        if ( !userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);

    }

    public UserDto getUserByEmail(String email) {
        return UserMapper.toDto(userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found with email: " + email)));
    }
    public void addTokenToBlackList(String token) {
        userCacheRepository.addTokenToBlackList(token);
    }
    public Boolean isTokenUnValid(String token) {
       return  userCacheRepository.tokenInBlackList(token);
    }
}
