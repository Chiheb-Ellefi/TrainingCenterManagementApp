package com.example.CenterManagement.entities.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TrainerType {
    @JsonProperty("INTERNAL")  INTERNAL,
    @JsonProperty("EXTERNAL")  EXTERNAL,
}
