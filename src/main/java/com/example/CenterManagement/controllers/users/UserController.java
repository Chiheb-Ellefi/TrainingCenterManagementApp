package com.example.CenterManagement.controllers.users;


import com.example.CenterManagement.dto.training.TrainingDto;
import com.example.CenterManagement.dto.user.ParticipantDto;
import com.example.CenterManagement.dto.user.TrainerDto;
import com.example.CenterManagement.dto.user.UserDto;
import com.example.CenterManagement.exceptions.users.UserNotFoundException;
import com.example.CenterManagement.models.requestData.UserRequestData;
import com.example.CenterManagement.services.training.TrainingEnrollmentService;
import com.example.CenterManagement.services.users.EmailService;
import com.example.CenterManagement.services.users.UserService;
import com.example.CenterManagement.utils.EnumsHelperMethods;
import com.example.CenterManagement.utils.RandomPasswordGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final TrainingEnrollmentService trainingEnrollmentService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserController(UserService userService, TrainingEnrollmentService trainingEnrollmentService,EmailService emailService, PasswordEncoder passwordEncoder) {
        this.userService = userService;

        this.trainingEnrollmentService = trainingEnrollmentService;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Operation(
            summary = "Get paginated list of all users",
            description = "Retrieves a list of users based on the provided page number. Default is page 0.",
            parameters = {
                    @Parameter(name = "page", description = "Page number for pagination")
            }
    )
    @ApiResponse(responseCode = "200", description = "List of users successfully retrieved",
            content = @Content(  mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = UserDto.class))))
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam(defaultValue = "0") Integer page) {
       List<UserDto> response= userService.getAllUsers(page);
        return ResponseEntity.ok().body(response);
    }
    @Operation(
            summary = "Get a user by ID",
            description = "Fetches a user using their unique ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found",content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "Provided user ID is null",content = @Content(mediaType = "text/plain"))
    })
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) throws BadRequestException {
        if(userId == null){
            throw new BadRequestException("Provided userId is null");
        }
      UserDto response=  userService.getUserById(userId);
      return ResponseEntity.ok().body(response);
    }
    @Operation(
            summary = "Create a new user",
            description = "Creates a new user with the provided role. Depending on the role, the response body varies: " +
                    "ADMIN/MANAGER → UserDto",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The necessary attributes to create a new user",
            content = @Content(schema = @Schema(implementation = UserRequestData.class)))
            )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "User created successfully. Response type depends on the user's role.",
                    content = @Content(schema = @Schema(
                            oneOf = {UserDto.class, TrainerDto.class, ParticipantDto.class}
                    ),mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid data was provided, check the request body",content = @Content(mediaType = "text/plain")
            )
    })
    @PostMapping
    public ResponseEntity<Object> createUser (@RequestBody UserRequestData data) throws BadRequestException {
        boolean wrongRequest=data==null || data.getUsername()==null || data.getEmail()==null || !EnumsHelperMethods.isValidRole(data.getRole());
        if(wrongRequest){
            throw new BadRequestException("Invalid input data, please try again");
        }
        String password = RandomPasswordGenerator.generateRandomPassword();
        UserDto userDto=UserDto.builder()
                .username(data.getUsername())
                .email(data.getEmail())
                .password(passwordEncoder.encode(password))
                .role(data.getRole())
                .description(data.getDescription())
                .dateOfBirth(data.getDateOfBirth())
                .phoneNumber(data.getPhoneNumber())
                .gender(data.getGender())
                .profilePicture(data.getProfilePicture())
                .build();
        UserDto response=userService.createUser(userDto);
        emailService.sendSimpleEmail(userDto.getEmail(), "An account with this email have been created",password);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @Operation(
            summary = "Update an existing user",
            description = "Updates fields of a user. Any null fields in the request body will keep their existing values.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The attributes the user want to update following the provided schema",
                    content = @Content(schema = @Schema(implementation = UserDto.class))
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "User successfully updated",
                    content = @Content(schema = @Schema(implementation = UserDto.class),mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid user ID provided",content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "User not found",content = @Content(mediaType = "text/plain"))
    })
    @PatchMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto,@PathVariable Long userId) throws BadRequestException {
        if(userId==null){
            throw new BadRequestException("Invalid user Id, please try again");
        }
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
                .profilePicture(userDto.getProfilePicture() != null ? userDto.getProfilePicture() : oldUser.getProfilePicture())
                .build();
        UserDto response=userService.updateUser(updatedUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @Operation(
            summary = "Delete a user by ID",
            description = "Deletes a user based on the provided user ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User successfully deleted",content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "Provided user ID is null",content = @Content(mediaType = "text/plain"))
    })
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) throws BadRequestException {
       if(userId==null){
           throw new BadRequestException("The provided user id is null");
       }
       userService.deleteUser(userId);
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @Operation(
            summary = "Get all training enrollments of a user",
            description = "Retrieves a list of trainings that the user (as a participant) is enrolled in."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Enrollments successfully retrieved",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TrainerDto.class)),mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Provided user ID is null",content = @Content(mediaType = "text/plain"))
    })
    @GetMapping("/{id}/enrollments")
    public ResponseEntity<List<TrainingDto>> getEnrollments(@PathVariable Long id) throws BadRequestException {
        if(id==null){
            throw new BadRequestException("The provided user id is null");
        }
        List<TrainingDto> enrollments=trainingEnrollmentService.getParticipantsEnrollment(id);
        return ResponseEntity.ok(enrollments);
    }




}
