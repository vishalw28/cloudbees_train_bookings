package com.cloudbees.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record BookingResponse(String pnr,User user, String coach, Integer seatNo,
                               String status, LocalDateTime journeyDateTime, LocalDateTime bookingDateTime, LocalDateTime modifiedDateTime) {
}
