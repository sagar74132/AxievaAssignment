package com.assignment.axievaassignment.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenericResponse<T> {

    private String message;
    private T data;
}
