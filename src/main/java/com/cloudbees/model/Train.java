package com.cloudbees.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.Set;


@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "train")
public class Train {
    @Id
    @NonNull
    Integer id;
    String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "train_coaches",
        joinColumns = {
            @JoinColumn(name = "train_no", referencedColumnName = "id")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "coach_no", referencedColumnName = "coach_no")
        }
    )
    Set<Coach> coaches;


    public Train(int id) {
        this.id = id;
    }
}
