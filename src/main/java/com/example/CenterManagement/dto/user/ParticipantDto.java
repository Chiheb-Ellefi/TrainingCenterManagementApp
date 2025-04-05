package com.example.CenterManagement.dto.user;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParticipantDto {
    private Long participantId;
    @JsonIgnoreProperties({"userId","password","role","isVerified","phoneNumber","secondPhoneNumber","dateOfBirth","description","gender"})
    private UserDto user;
    private String structure ;
    private String profile;

}
