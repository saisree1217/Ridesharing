package com.gocarshare.scheduleservice.scheduleservice.Modal;

import com.gocarshare.scheduleservice.scheduleservice.form.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookedReservations {

  private String reservationId;
  private Date reservationStartDate;
  private Date reservationEndDate;
  private Location pickUpLocation;
  private String lendeeId;
  private double reservationPrice;

  private String reservationStatus; //CONFIRMED, COMMUTING, Closed
}
