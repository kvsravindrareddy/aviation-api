package com.ravindra.exceptions;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomDataException extends RuntimeException{
    private String msg;
}