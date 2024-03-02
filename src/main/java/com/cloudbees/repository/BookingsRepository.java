package com.cloudbees.repository;

import com.cloudbees.model.Bookings;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
public interface BookingsRepository extends CrudRepository<Bookings, Integer> {

    List<Bookings> findByTrainNoAndJourneyDateTimeAndBookingStatus(Integer trainNo, LocalDateTime journeyDateTime, Integer status);
}
