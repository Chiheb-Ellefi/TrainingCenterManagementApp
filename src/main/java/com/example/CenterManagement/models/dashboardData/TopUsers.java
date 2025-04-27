package com.example.CenterManagement.models.dashboardData;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public  class TopUsers {
   private  String username;
   private  long nb;
  private  String Domain;
}
