package com.gocarshare.scheduleservice.scheduleservice.Modal;


import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Reservations")
public class Reservations {

    private String _id;
    private String lenderId;
    private String lendeeId;
    private String listingId;

    private String reservationStatus;

    private Date startDate;
    private Date endDate;

    private double reservationPrice;

}
