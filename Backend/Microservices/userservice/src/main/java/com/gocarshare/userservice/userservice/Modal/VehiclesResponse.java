package com.gocarshare.userservice.userservice.Modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiclesResponse {

    private String userId;
    private int total;
    private List<UserVehicle> userVehicles;

}
