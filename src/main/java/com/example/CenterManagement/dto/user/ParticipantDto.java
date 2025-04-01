package com.example.CenterManagement.dto.user;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ParticipantDto {
    private Long participantId;
    private UserDto user;
    private String structure ;
    private String profile;

}
