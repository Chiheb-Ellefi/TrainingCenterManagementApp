package com.example.CenterManagement.entities.training;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Type {
    @JsonProperty("REMOTE")  REMOTE,
    @JsonProperty("ONSITE") ONSITE,
    @JsonProperty("HYBRID") HYBRID
}
