package com.cloudbees.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record BookingRequest(int userId, int trainNo, String src, String dest,
                             @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss")
                             LocalDateTime journeyDateTime, String coachType) {
}
