package com.siarhei.eventmanagement.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import com.siarhei.eventmanagement.payload.Event;

@Component
public class EventDatesValidator implements ConstraintValidator<EventDatesValidation, Event> {

  @Override
  public boolean isValid(Event value, ConstraintValidatorContext context) {
    return value.getFinish().isAfter(value.getStart());
  }

}