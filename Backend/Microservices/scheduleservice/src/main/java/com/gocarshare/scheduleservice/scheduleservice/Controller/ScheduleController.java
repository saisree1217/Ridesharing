package com.gocarshare.scheduleservice.scheduleservice.Controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

  @GetMapping("/test")
  public String test(){
    return "this Thing is working";
  }
}
