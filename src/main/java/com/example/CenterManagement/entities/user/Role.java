package com.example.CenterManagement.entities.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Role {
    @JsonProperty("ADMIN")  ADMIN,
    @JsonProperty("MANAGER")    MANAGER,
    @JsonProperty("PARTICIPANT")   PARTICIPANT,
    @JsonProperty("TRAINER")   TRAINER,
    @JsonProperty("SIMPLE") SIMPLE

}

