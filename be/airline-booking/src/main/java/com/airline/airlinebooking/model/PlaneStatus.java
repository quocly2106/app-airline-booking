package com.airline.airlinebooking.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PlaneStatus {
    AVAILABLE, MAINTENANCE, FLIGHT,DELAYED,SCHEDULED, CANCELLED;

}
