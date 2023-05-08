package com.gocarshare.scheduleservice.scheduleservice.Modal;

import com.gocarshare.scheduleservice.scheduleservice.form.Location;
import com.google.firebase.database.annotations.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "VehicleListings")
@Builder
public class ListedVehicle {

    private String _id;
    private String ownerId;

    private String vehicleId;
    private Date availableFrom;
    private Date availableTill;
    private double listingPrice;
    private Date ListedOn;
    private boolean listed;

    private List<BookedReservations> activeReservations;
    private Location vehicleLocation;
    private boolean listingVisible;
}
