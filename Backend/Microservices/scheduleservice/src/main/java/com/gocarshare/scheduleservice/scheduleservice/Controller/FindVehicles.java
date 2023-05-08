package com.gocarshare.scheduleservice.scheduleservice.Controller;

import com.gocarshare.scheduleservice.scheduleservice.Modal.User;
import com.gocarshare.scheduleservice.scheduleservice.Service.FindVehiclesService;
import com.gocarshare.scheduleservice.scheduleservice.Service.ReservationService;
import com.gocarshare.scheduleservice.scheduleservice.form.FindVehiclesForm;
import com.gocarshare.scheduleservice.scheduleservice.form.ReservationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/find")
public class FindVehicles {


  @Autowired
  FindVehiclesService fvs;

  @Autowired
  ReservationService reservationService;
  @PostMapping("/find-vehicles")
  public ResponseEntity<?> getQuery(@RequestBody FindVehiclesForm form){
    return new ResponseEntity<>(fvs.findVehicles(form), HttpStatus.OK);
  }

  @PostMapping("/reserve")
  public ResponseEntity<?> reserve(@RequestBody ReservationForm form, Authentication auth){
    User user = (User) auth.getPrincipal();
    return new ResponseEntity<>( reservationService.reserveVehicle(form, user.get_id()), HttpStatus.OK);
  }

  @GetMapping("/myreservations")
  public ResponseEntity<?> myreservations(Authentication auth){
    User user = (User) auth.getPrincipal();
    return new ResponseEntity<>( reservationService.getMyReservations(user.get_id()), HttpStatus.OK);
  }

}
