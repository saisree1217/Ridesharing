package com.gocarshare.scheduleservice.scheduleservice.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Data
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class ExceptionResponse extends RuntimeException {

  private final int status;
  private final String message;

  @Override
  public String getMessage() {
    return status + ", " + message;
  }
}
