package com.cloudbees.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "train_fare")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TrainFare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    Integer trainNo;
    String src;
    String dest;
    Integer coachType;
    Integer fare;

    public TrainFare(Integer trainNo, String src,
                     String dest, Integer coachType, Integer fare) {
        this.trainNo = trainNo;
        this.src = src;
        this.dest = dest;
        this.coachType = coachType;
        this.fare = fare;
    }
}
