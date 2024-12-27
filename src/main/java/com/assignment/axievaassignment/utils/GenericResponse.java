package com.assignment.axievaassignment.utils;

import lombok.Getter;
import lombok.Setter;

/**
 * Generic response class to return response with message and data
 *
 * @param <T> POJO type
 */
@Getter
@Setter
public class GenericResponse<T> {

    private String message;
    private T data;
}
