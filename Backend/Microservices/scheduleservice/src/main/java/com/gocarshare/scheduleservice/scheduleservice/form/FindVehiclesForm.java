package com.gocarshare.scheduleservice.scheduleservice.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindVehiclesForm {

  private int radius;
  private double latitude;
  private double longitude;
  private String startDate;
  private String endDate;
}
