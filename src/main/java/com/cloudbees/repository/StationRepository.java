package com.cloudbees.repository;

import com.cloudbees.model.Station;
import org.springframework.data.repository.CrudRepository;

public interface StationRepository extends CrudRepository<Station, String> {
}
