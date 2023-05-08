package com.gocarshare.scheduleservice.scheduleservice.Service;

import com.gocarshare.scheduleservice.scheduleservice.Exception.ExceptionResponse;
import com.gocarshare.scheduleservice.scheduleservice.Modal.ListedVehicle;
import com.gocarshare.scheduleservice.scheduleservice.Modal.UserVehicle;
import com.gocarshare.scheduleservice.scheduleservice.Util.DateParseUtil;
import com.gocarshare.scheduleservice.scheduleservice.form.ListVehicleForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Service
public class ListingService {
  @Autowired
  MongoTemplate mongoTemplate;

  public ListedVehicle listVehicle(ListVehicleForm form, String userId) throws ParseException {

    Query q = Query.query(
      Criteria.where("ownerId").is(userId)
        .and("_id").is(form.getVehicleId())
    );

    // Find user Vehicle
    UserVehicle userVehicle = mongoTemplate.findOne(q, UserVehicle.class, "UserRegisteredVehicles");

    if(userVehicle == null){
      throw new ExceptionResponse(HttpStatus.NOT_FOUND.value(), "No such user vehicle");
    }

    // Search if anyone has registered that vehicle in the given period of time already from ReservationsCollection.
    Query findListingQuery = Query.query(
      Criteria.where("vehicleId")
        .is(form.getVehicleId())
        .and("ownerId").is(userId)
    );

    ListedVehicle listedVehicle = mongoTemplate.findOne(
      findListingQuery,
      ListedVehicle.class,
      "VehicleListings");

    if(listedVehicle != null && listedVehicle.isListed()){
      throw new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), "Vehicle is already listed");
    }


    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy:hh:mm:a");
    dateFormat.setTimeZone(TimeZone.getTimeZone("EST"));

    ListedVehicle newListing = ListedVehicle
      .builder()
      .ownerId(userId)
      .ListedOn(new Date())
      .availableFrom(DateParseUtil.parseDate(form.getAvailableFrom()))
      .availableTill(DateParseUtil.parseDate(form.getAvailableTill()))
      .vehicleId(form.getVehicleId())
      .listingPrice(form.getListingPrice())
      .listed(Boolean.TRUE)
      .listingVisible(Boolean.TRUE)
      .vehicleLocation(form.getVehicleLocation())
      .activeReservations(new ArrayList<>())
      .build();

      Update update = new Update();
      update.set("availabilityStatus", "LISTED");

      mongoTemplate.updateFirst(
        Query.query(Criteria.where("_id").is(form.getVehicleId()).and("ownerId").is(userId)),
        update,
        "UserRegisteredVehicles"
      );
    assert listedVehicle != null;
    return mongoTemplate.insert(newListing);
  }
}
