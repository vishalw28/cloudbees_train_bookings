package com.cloudbees.model;

import com.cloudbees.repository.StationRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "station")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Station{
    @Id String code;
    String name;
}
