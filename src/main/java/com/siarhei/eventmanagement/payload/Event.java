package com.siarhei.eventmanagement.payload;

import java.time.LocalDateTime;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class Event {

  @NotNull
  private Long id;
  @NotBlank
  private String title;
  @FutureOrPresent
  private LocalDateTime start;
  @Future
  private LocalDateTime finish;

}
