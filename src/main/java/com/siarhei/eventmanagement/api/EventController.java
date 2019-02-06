package com.siarhei.eventmanagement.api;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.siarhei.eventmanagement.payload.Event;
import com.siarhei.eventmanagement.validation.EventDatesValidation;

@RestController
@Validated
public class EventController {

  private Map<Long, Event> db = new HashMap<>();

  @PostMapping("/events")
  public ResponseEntity<Void> addEvent(
      @RequestBody @Valid @EventDatesValidation Event event,
      UriComponentsBuilder builder) {
    db.put(event.getId(), event);
    UriComponents uriComponents = builder.path("/events/{eventId}").buildAndExpand(event.getId());
    return ResponseEntity.created(uriComponents.toUri()).build();
  }

  @GetMapping("/events/{eventId}")
  public ResponseEntity<Event> getCourse(
      @PathVariable("eventId") Long eventId) {
    return ResponseEntity.ok().body(db.get(eventId));
  }
}
