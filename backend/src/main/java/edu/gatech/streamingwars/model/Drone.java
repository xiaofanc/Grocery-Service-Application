package edu.gatech.streamingwars.model;

import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String droneId;

    private int capacity;
    private int remainingCapacity;
    private int fuel;
    private int numOrders = 0;
    private String pilotName;
    private String pilotAccount;

    public Drone(String droneId, int capacity, int fuel){
        this.setDroneId(droneId);
        this.setCapacity(capacity);
        this.setRemainingCapacity(capacity);
        this.setFuel(fuel);
    }

    public void setDroneId(String droneId) {
        this.droneId = droneId;
    }

    public String getDroneId() {
        return droneId;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setRemainingCapacity(int remainingCapacity) {
        this.remainingCapacity = remainingCapacity;
    }

    public int getRemainingCapacity() {
        return remainingCapacity;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public int getFuel() {
        return fuel;
    }

    public void setPilot(String pilotName, String pilotAccount) {
        this.pilotName = pilotName;
        this.pilotAccount = pilotAccount;
    }

    public String getPilotName() {
        return pilotName;
    }

    public int getNumOrders() {
        return numOrders;
    }

    public void setNumOrders(int numOrders) {
        this.numOrders = numOrders;
    }

    public String getPilotAccount() {
        return pilotAccount;
    }
}
