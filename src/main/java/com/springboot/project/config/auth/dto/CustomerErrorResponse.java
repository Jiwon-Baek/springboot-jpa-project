package com.springboot.project.config.auth.dto;
import lombok.Data;

@Data
public class CustomerErrorResponse {

    private int statusCode;

    private String message;

    private long timestamp;
}