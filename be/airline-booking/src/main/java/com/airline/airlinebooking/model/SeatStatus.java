package com.airline.airlinebooking.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SeatStatus {
    AVAILABLE, RESERVED, CANCELLED;

    @JsonCreator
    public static SeatStatus fromString(String value) {
        if (value == null) {
            return null;
        }
        // Chuyển chuỗi thành enum, loại bỏ dấu cách thừa và so sánh không phân biệt chữ hoa/thường
        return SeatStatus.valueOf(value.trim().toUpperCase());
    }

    @JsonValue
    public String toJson() {
        return name();
    }
}

