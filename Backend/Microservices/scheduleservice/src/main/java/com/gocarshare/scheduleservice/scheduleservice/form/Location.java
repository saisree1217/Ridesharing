package com.gocarshare.scheduleservice.scheduleservice.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {

  private String type = "Point";
  private List<Integer> coordinates;
}
