package com.seng.management_system.apiResponse;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
    private String status;
    private T data;
    private Object errors;
    private LocalDateTime timestamp = LocalDateTime.now();

    public ApiResponse(String status, T data, Object errors){
        this.status = status;
        this.data = data;
        this.errors = errors;
    }

    public static <T> ApiResponse<T> success(T data){
        return new ApiResponse<>("Success", data, null);
    }

    public static <T> ApiResponse<T> fail(Object error){
        return new ApiResponse<>("Fail", null, error);
    }
}
