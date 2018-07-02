package com.valid.demo.valid;

import com.valid.demo.valid.reps.RepValidError;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BodyValid {

    public RepValidError validErros(BindingResult result) {
        RepValidError rep = new RepValidError();
        if (result.hasErrors()) {
            List<FieldError> errors = result.getFieldErrors();
            Map<String, String> map = new HashMap<>();
            for (FieldError error : errors) {
                map.put(error.getField(), error.getDefaultMessage());
            }
            rep.setErrors(map);
            return rep;
        }
        return null;
    }

}
