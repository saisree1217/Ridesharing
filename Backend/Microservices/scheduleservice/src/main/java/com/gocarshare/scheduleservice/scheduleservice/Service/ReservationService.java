package com.gocarshare.scheduleservice.scheduleservice.Service;

import com.gocarshare.scheduleservice.scheduleservice.Exception.ExceptionResponse;
import com.gocarshare.scheduleservice.scheduleservice.Modal.BookedReservations;
import com.gocarshare.scheduleservice.scheduleservice.Modal.ListedVehicle;
import com.gocarshare.scheduleservice.scheduleservice.Modal.Reservations;
import com.gocarshare.scheduleservice.scheduleservice.Util.DateParseUtil;
import com.gocarshare.scheduleservice.scheduleservice.form.ReservationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ReservationService {

  @Autowired
  MongoTemplate mongoTemplate;
  public Object reserveVehicle(ReservationForm form, String userId){

    Criteria findById = Criteria.where("_id").is(form.getListingId());

    Criteria noActiveReservationsQuery = new Criteria().orOperator(
      Criteria.where("activeReservations").size(0),
      Criteria.where("activeReservations").elemMatch(
        Criteria.where("reservationStartDate").lte(form.getStartDate())
          .and("reservationEndDate").gte(form.getEndDate())
      )
    );

    Criteria dateRangeQuery = new Criteria().andOperator(
      Criteria.where("availableFrom").lte(form.getStartDate()).and("availableTill").gte(form.getEndDate()));

    Criteria listedOnly = new Criteria().andOperator(Criteria.where("listed").is(true));
    Criteria listingVisible = new Criteria().andOperator(Criteria.where("listingVisible").is(true));

    // Create a Criteria for the complete query
    Criteria query = new Criteria()
      .andOperator(
        listedOnly,
        listingVisible
      );

    // Create a Query object with projection
    Query mongoQuery = new Query(query);

    // Use the MongoTemplate to execute the query
    ListedVehicle listedVehicle = mongoTemplate
      .findOne(
        mongoQuery,
        ListedVehicle.class,
        "VehicleListings");

    if(listedVehicle == null || listedVehicle.get_id() == null ){
      throw new ExceptionResponse(HttpStatus.NOT_FOUND.value(), "No vehicles available");
    }

    if(listedVehicle.getOwnerId().equals(userId)){
      throw new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), "Can't reserve your own Vehicle");
    }

    String uuid = UUID.randomUUID().toString();


    Reservations reservation = new Reservations();
    reservation.set_id(uuid);
    reservation.setLenderId(listedVehicle.getOwnerId());
    reservation.setLendeeId(userId);
    reservation.setListingId(listedVehicle.get_id());
    reservation.setStartDate(DateParseUtil.parseDate(form.getStartDate()));
    reservation.setEndDate(DateParseUtil.parseDate(form.getEndDate()));
    reservation.setReservationStatus("RESERVED");
    reservation.setReservationPrice(listedVehicle.getListingPrice());

    BookedReservations br = new BookedReservations();
    br.setReservationId(uuid);
    br.setLendeeId(userId);
    br.setReservationStartDate(DateParseUtil.parseDate(form.getStartDate()));
    br.setReservationEndDate(DateParseUtil.parseDate(form.getEndDate()));
    br.setPickUpLocation(listedVehicle.getVehicleLocation());
    br.setReservationPrice(listedVehicle.getListingPrice());


    Update bookedReservationUpdate = new Update();
    bookedReservationUpdate.push("activeReservations", br);

    mongoTemplate.updateFirst(
      Query.query(Criteria.where("_id").is(listedVehicle.getVehicleId())),
        bookedReservationUpdate,
        ListedVehicle.class
      );

    return mongoTemplate.insert(reservation);
  }


  public Map<String, List<Reservations>> getMyReservations(String userId){

    Query query = new Query(Criteria.where("lendeeId").is(userId));
    List<Reservations> reservations =  mongoTemplate.find(query, Reservations.class, "Reservations");

    Map<String,List<Reservations>>  resp = new HashMap<>();
    resp.put("userReservations",reservations);
    return resp;
  }
}
