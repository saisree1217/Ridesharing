package com.gocarshare.userservice.userservice.Services;

import com.gocarshare.userservice.userservice.Modal.UserVehicle;
import com.gocarshare.userservice.userservice.Modal.VehiclesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleRegistrationService {

    @Autowired
    MongoTemplate mongoTemplate;

    public UserVehicle registerVehicle(String userId, UserVehicle userVehicle){

        userVehicle.setAvailabilityStatus("UN_LISTED");
        userVehicle.setOwnerId(userId);
        userVehicle.setDisabled(Boolean.FALSE);
        userVehicle.setRegisteredOn(new Date());

        return mongoTemplate.insert(userVehicle, "UserRegisteredVehicles");

    }

    public VehiclesResponse getUserVehicles(String userId){

        Query query = Query.query(Criteria.where("ownerId").is(userId));

        List<UserVehicle> result = mongoTemplate.find(query, UserVehicle.class, "UserRegisteredVehicles");
        result = result.stream()
                .sorted(Comparator.comparing(UserVehicle::getRegisteredOn)
                        .reversed())
                .collect(Collectors.toList());

        if(result.isEmpty()) {
            return new VehiclesResponse(userId, 0, new ArrayList<>());
        }
        return new VehiclesResponse(userId, result.size(), result);
    }

}
