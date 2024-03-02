package com.cloudbees.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Entity(name = "coach")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "coach")
public class Coach {

    @Id
    @Column(name = "coach_no")
    String coachNo;
    int type;
    Integer seats;
    @ManyToMany(mappedBy = "coaches", fetch = FetchType.LAZY)
    Set<Train> train;

    public Coach(String coachNo, Integer type, Integer seats) {
        this.coachNo = coachNo;
        this.type = type;
        this.seats = seats;
    }

}
