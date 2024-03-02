package com.cloudbees.repository;

import com.cloudbees.model.Train;
import org.springframework.data.repository.CrudRepository;

public interface TrainRepository extends CrudRepository<Train, Integer> {
}
