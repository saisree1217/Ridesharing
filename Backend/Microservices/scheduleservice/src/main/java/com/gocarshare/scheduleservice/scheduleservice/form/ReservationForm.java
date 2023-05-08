package com.gocarshare.scheduleservice.scheduleservice.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationForm {

  private String startDate;
  private String endDate;
  private String listingId;
}
