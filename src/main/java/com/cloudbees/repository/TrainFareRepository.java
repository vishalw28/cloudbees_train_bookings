package com.cloudbees.repository;

import com.cloudbees.model.TrainFare;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TrainFareRepository extends CrudRepository<TrainFare, Integer> {

    Optional<TrainFare> findByTrainNoAndSrcAndDestAndCoachType(Integer trainNo, String src, String dest, Integer coachType);
}
