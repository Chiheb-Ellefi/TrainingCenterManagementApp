package com.example.CenterManagement.controllers;

import com.example.CenterManagement.dto.user.UserDto;
import com.example.CenterManagement.exceptions.UserNotFoundException;
import com.example.CenterManagement.services.users.UserService;
import com.example.CenterManagement.utils.EnumsHelperMethods;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam(defaultValue = "0") Integer page) {
       List<UserDto> response= userService.getAllUsers(page);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
      UserDto response=  userService.getUserById(userId);
      return ResponseEntity.ok().body(response);
    }
    @PostMapping
    public ResponseEntity<UserDto> createUser (@RequestBody UserDto userDto) throws BadRequestException {
        boolean wrongRequest=userDto==null || userDto.getUsername()==null || userDto.getEmail()==null || !EnumsHelperMethods.isValidRole(userDto.getRole());
        if(wrongRequest){
            throw new BadRequestException("Invalid input data, please try again");
        }
        UserDto response=userService.createUser(userDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PatchMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto,@PathVariable Long userId) throws BadRequestException {
        UserDto oldUser=userService.getUserById(userId);
        if(oldUser==null){
            throw new UserNotFoundException("No user found with id "+userId);
        }
        UserDto updatedUser = UserDto.builder()
                .username(userDto.getUsername() != null ? userDto.getUsername() : oldUser.getUsername())
                .email(userDto.getEmail() != null ? userDto.getEmail() : oldUser.getEmail())
                .password(userDto.getPassword() != null ? userDto.getPassword() : oldUser.getPassword())
                .role(userDto.getRole() != null ? userDto.getRole() : oldUser.getRole())
                .dateOfBirth(userDto.getDateOfBirth() != null ? userDto.getDateOfBirth() : oldUser.getDateOfBirth())
                .description(userDto.getDescription() != null ? userDto.getDescription() : oldUser.getDescription())
                .userId(userId)
                .gender(userDto.getGender() != null ? userDto.getGender() : oldUser.getGender())
                .phoneNumber(userDto.getPhoneNumber() != null ? userDto.getPhoneNumber() : oldUser.getPhoneNumber())
                .secondPhoneNumber(userDto.getSecondPhoneNumber() != null ? userDto.getSecondPhoneNumber() : oldUser.getSecondPhoneNumber())
                .isVerified(userDto.getIsVerified() != null ? userDto.getIsVerified() : oldUser.getIsVerified())
                .profilePicture(userDto.getProfilePicture() != null ? userDto.getProfilePicture() : oldUser.getProfilePicture())
                .build();
        UserDto response=userService.updateUser(updatedUser);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) throws BadRequestException {
       if(userId==null){
           throw new BadRequestException("The provided user id is null");
       }
       userService.deleteUser(userId);
       return new ResponseEntity<>("User deleted successfully!",HttpStatus.ACCEPTED);
    }

}
