package com.gocarshare.scheduleservice.scheduleservice.Service;

import com.gocarshare.scheduleservice.scheduleservice.Modal.ListedVehicle;
import com.gocarshare.scheduleservice.scheduleservice.Util.DateParseUtil;
import com.gocarshare.scheduleservice.scheduleservice.form.FindVehiclesForm;
import com.google.gson.Gson;
import org.bson.Document;
import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import java.util.*;


@Service
public class FindVehiclesService {



    @Autowired private MongoTemplate mongoTemplate;

    public  Map<String, List<ListedVehicle>> findVehicles(FindVehiclesForm form) {

      Date startDt = DateParseUtil.parseDate(form.getStartDate());
      Date endDt = DateParseUtil.parseDate(form.getEndDate());


      // Create a BasicDBObject representing the coordinates for the center of the circle
      Document coordinates = new Document();
      coordinates.append("type", "Point");
      coordinates.append("coordinates", Arrays.asList(form.getLongitude(), form.getLatitude()));


      // Create a BasicDBObject representing the location query
      Document geometry = new Document();
      geometry.put("$geometry", coordinates);
      geometry.append("$minDistance", 0);
      geometry.append("$maxDistance", form.getRadius() * 1609.34);
      //geometry.put("$maxDistance", radius * 1609.34); // Convert miles to meters
      Document locationQuery = new Document("vehicleLocation",
        new Document("$nearSphere", geometry)
      );


      //Document query = new Document("activeReservations.zip", new Document("$gte", minZip).append("$lte", maxZip));
      List<Document> orConditions = new ArrayList<>();
      orConditions.add(new Document("activeReservations", new Document("$size", 0)));


      Document noActiveReservationsQuery = new Document()
        .append(
          "activeReservations", new Document("$not", new Document("$elemMatch", new Document()
            .append("reservationStartDate", new Document("$lte", startDt))
            .append("reservationEndDate", new Document("$gte", endDt))))
        );

      orConditions.add(noActiveReservationsQuery);

      List<Document> filterslist = new ArrayList<>();
      // Or conditions query that contains the no Active reservations and outside the range are put here
      filterslist.add(new Document("$or", orConditions));
      filterslist.add(locationQuery);


      Document projection = new Document("ownerId", 1)
        .append("vehicleId", 1)
        .append("availableFrom", 1)
        .append("availableTill", 1)
        .append("listingPrice", 1);


      Document andConditions = new Document("$and", filterslist);


      Query queryObj = new BasicQuery(andConditions.toJson());

//      return mongoTemplate.findAll(queryObj, "VehicleListings", ListedVehicle.class);

//      AggregationOperation matchAggregationStage = context -> context.getMappedObject(Document.parse(andConditions.toJson()));
////      AggregationOperation projectionAggregationStage = context -> context.getMappedObject(Document.parse(projection.toJson()));
//
//      TypedAggregation<ListedVehicle> aggregation = Aggregation.newAggregation(
//        ListedVehicle.class, matchAggregationStage);
//
//
//      AggregationResults<ListedVehicle> results = mongoTemplate.find(Document.parse(andConditions.toJson()), "VehicleListings", ListedVehicle.class);
////      AggregationResults<ListedVehicle> results = mongoTemplate.aggregate(aggregation,
////        "VehicleListings",
////        ListedVehicle.class);

      return driverCode(form);
    }


    public Map<String, List<ListedVehicle>> driverCode(FindVehiclesForm form) {
      Date startDt = DateParseUtil.parseDate(form.getStartDate());
      Date endDt = DateParseUtil.parseDate(form.getEndDate());

      // Create a Criteria representing the coordinates for the center of the circle
      Criteria coordinates = Criteria.where("vehicleLocation")
        .near(new Point(form.getLongitude(), form.getLatitude()))
        .minDistance(0)
        .maxDistance(form.getRadius() * 1609.34); // Convert miles to meters

      // Create a Criteria representing the location query
      Criteria locationQuery = new Criteria().andOperator(coordinates);

      // Create a Criteria for the no active reservations query
      Criteria noActiveReservationsQuery = new Criteria().orOperator(
        Criteria.where("activeReservations").size(0),
        Criteria.where("activeReservations").not().elemMatch(
          Criteria.where("reservationStartDate").lte(startDt)
            .and("reservationEndDate").gte(endDt)
        )
      );

//      Document dateRangeQuery = new Document("availableFrom", gteDate).append("availableTill", lteDate);

      Criteria dateRangeQuery = new Criteria().andOperator(
        Criteria.where("availableFrom").lte(startDt).and("availableTill").gte(endDt));

      Criteria listedOnly = new Criteria().andOperator(Criteria.where("listed").is(true));
      Criteria listingVisible = new Criteria().andOperator(Criteria.where("listingVisible").is(true));

      // Create a Criteria for the complete query
      Criteria query = new Criteria()
        .andOperator(
          noActiveReservationsQuery,
//        locationQuery,
        dateRangeQuery,
        listedOnly,
        listingVisible
        );

      // Create a Query object with projection
      Query mongoQuery = new Query(query);

      // Use the MongoTemplate to execute the query
      List<ListedVehicle> result = mongoTemplate.find(mongoQuery, ListedVehicle.class);

      Map<String, List<ListedVehicle>> res = new HashMap<>();
      res.put("vehicles", result);
      return res;
    }
}
