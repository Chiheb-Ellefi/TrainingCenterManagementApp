package com.example.CenterManagement.models;


import lombok.Getter;

import java.util.Date;
@Getter
public class TrainingRequestData {
    private String title;
    private Date startDate;
    private Date endDate;
    private String description;
    private String domainName;
    private Long trainerId;
}
