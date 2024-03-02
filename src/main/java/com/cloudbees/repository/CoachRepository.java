package com.cloudbees.repository;

import com.cloudbees.model.Coach;
import org.springframework.data.repository.CrudRepository;

public interface CoachRepository extends CrudRepository<Coach, String> {
}
