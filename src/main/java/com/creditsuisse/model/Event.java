package com.creditsuisse.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "EVENT_DETAILS")
public class Event{
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "EVENT_DURATION")
    private long eventDuration;

    @Column(name = "TYPE")
    private String logType;

    @Column(name = "HOST")
    private String host;

    @Column(name = "ALERT")
    private Boolean alert;

}
