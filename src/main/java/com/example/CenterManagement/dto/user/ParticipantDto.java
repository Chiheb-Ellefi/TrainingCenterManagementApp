package com.example.CenterManagement.dto.user;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ParticipantDto {
    private Long participantId;
    private UserDto user;
    private String structure ;
    private String profile;

}
