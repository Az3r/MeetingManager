package com.azer.meetingmanager.data.models;

import javax.persistence.*;

@Entity
public class Location {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int locationId;

    @Column(length = 100)
    private String name;

    @Column(length = 100)
    private String address;

    @Column
    private int capacity;

    @OneToOne()
    private Meeting meeting;

    public int getLocationId() {
        return locationId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}