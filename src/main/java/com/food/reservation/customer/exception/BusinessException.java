package com.food.reservation.exception;

import lombok.*;

@AllArgsConstructor
@Getter @Setter
@NoArgsConstructor
@Builder
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    @Builder.Default
    private String errorDescription = "The system had an internal exception";

    private String errorCode;

    private String errorParam;

    public BusinessException(String code, String description) {
        this.errorCode = code;
        this.errorDescription = description;
    }
}