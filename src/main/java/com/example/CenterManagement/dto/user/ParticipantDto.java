package com.example.CenterManagement.dto.user;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ParticipantDto {
    private Long participantId;
    @JsonIgnoreProperties({"password","role","isVerified","phoneNumber","secondPhoneNumber","dateOfBirth","description","gender"})
    private UserDto user;
    private String structure ;
    private String profile;

}
