package com.example.CenterManagement.controllers.users;

import com.example.CenterManagement.dto.user.UserDto;
import com.example.CenterManagement.models.requestData.LoginRequestData;
import com.example.CenterManagement.models.requestData.UserRequestData;
import com.example.CenterManagement.security.JwtUtils;
import com.example.CenterManagement.services.users.UserService;
import com.example.CenterManagement.utils.EnumsHelperMethods;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
   private final  PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthenticationController(AuthenticationManager authenticationManager, UserService userService, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestData data) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        data.getEmail(),
                        data.getPassword()
                )
        );

        authentication.getPrincipal();
        UserDto userDto = userService.getUserByEmail(data.getEmail());
        String token = jwtUtils.generateToken(userDto);
        Map<String, Object> response = new HashMap<>();
        response.put("email", userDto.getEmail());
        response.put("token", token);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequestData data) throws BadRequestException {
        boolean wrongRequest=data==null || data.getUsername()==null || data.getEmail()==null || !EnumsHelperMethods.isValidRole(data.getRole());
        if(wrongRequest){
            throw new BadRequestException("Invalid input data, please try again");
        }
        UserDto userDto=UserDto.builder()
                .username(data.getUsername())
                .email(data.getEmail())
                .password(passwordEncoder.encode(data.getPassword()))
                .role(data.getRole())
                .description(data.getDescription())
                .dateOfBirth(data.getDateOfBirth())
                .phoneNumber(data.getPhoneNumber())
                .gender(data.getGender())
                .profilePicture(data.getProfilePicture())
                .build();
        UserDto response=userService.createUser(userDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletRequest request) {
       String token= request.getHeader("Authorization").split("Bearer ")[1];
       log.info(token);
        userService.addTokenToBlackList(token);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
