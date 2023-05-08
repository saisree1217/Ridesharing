package com.gocarshare.userservice.userservice.Services;


import com.gocarshare.userservice.userservice.Exception.ExceptionResponse;
import com.gocarshare.userservice.userservice.Modal.User;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.UserRecord;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
//    @PostConstruct

    @Autowired
    private Firestore db;

    @Autowired private MongoTemplate mongoTemplate;
    public void createUserInMongoDb(UserRecord userRecord) {

        User user = new User();
        user.set_id(userRecord.getUid());
        user.setDisplayName(userRecord.getDisplayName());
        user.setEmail(userRecord.getEmail());
        user.setRoles(new ArrayList<>());
        user.setUserType("");

       User results = mongoTemplate.save(user);
    }


    public User getUserDetails(String uid){
        Query query = Query.query(Criteria.where("_id").is(uid));

        User user = mongoTemplate.findOne(query, User.class);

        if(user == null){
            throw new ExceptionResponse(HttpStatus.NOT_FOUND.value(), "User not found");
        }

        return user;
    }

    public Object setUserType(String userId, String userType){

        Query query = Query.query(Criteria.where("_id").is(userId));

        Update update = new Update();
        update.set("userType", userType);

        UpdateResult result  =mongoTemplate.updateFirst(query, update, User.class, "Users");

        Map<String, String> resp = new HashMap<>();
        if(result.getModifiedCount() > 0){
            resp.put("message", "updated");
            return  resp;
        }
        throw  new ExceptionResponse(406, "Bad Request");
    }

}
