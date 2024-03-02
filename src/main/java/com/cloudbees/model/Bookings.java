package com.cloudbees.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "bookings")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Bookings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name = "journey_date")
    LocalDateTime journeyDateTime;
    @Column(name = "booked_datetime")
    LocalDateTime bookingDateTime;
    @Column(name = "modified_datetime")
    LocalDateTime modifiedDateTime;
    @Column(name = "user_id")
    Integer userId;
    @Column(name = "train_no")
    Integer trainNo;
    String src;
    String dest;
    Integer amount;
    @Column(name = "booking_status")
    Integer bookingStatus;
    String coachNo;
    Integer seatNo;



}
