package com.gocarshare.scheduleservice.scheduleservice.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListVehicleForm {

  private String vehicleId;
  private Location vehicleLocation;
  private double listingPrice;
  private String availableFrom;
  private String availableTill;
}
