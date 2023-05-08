package com.gocarshare.scheduleservice.scheduleservice.Controller;


import com.gocarshare.scheduleservice.scheduleservice.Modal.ListedVehicle;
import com.gocarshare.scheduleservice.scheduleservice.Modal.User;
import com.gocarshare.scheduleservice.scheduleservice.Service.ListingService;
import com.gocarshare.scheduleservice.scheduleservice.form.ListVehicleForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/list")
public class ListingController {

  @Autowired
  ListingService listingService;
  @PostMapping("/list-vehicle")
  public ResponseEntity<?> listVehicle(@RequestBody ListVehicleForm listVehicleForm, Authentication auth) throws ParseException {
    User user = (User) auth.getPrincipal();

    return new ResponseEntity<>(listingService.listVehicle(listVehicleForm,  user.get_id()), HttpStatus.OK);
  }

}
