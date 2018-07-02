package com.valid.demo.reps;

import lombok.Data;

import java.util.Map;

@Data
public class RepValidError {

    private Map<String,String> errors;
}
