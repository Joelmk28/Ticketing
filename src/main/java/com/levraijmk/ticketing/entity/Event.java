package com.levraijmk.ticketing.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "event")
public class Event {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "total_capacity")
    private long totalCapacity;
    @Column(name = "left_capacity")
    private long leftCapacity;


    //Relation

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

}
