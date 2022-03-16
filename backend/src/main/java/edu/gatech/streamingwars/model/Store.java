package edu.gatech.streamingwars.model;

import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@NoArgsConstructor
public class Store implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long store_id;
    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(nullable = false)
    private int revenue;

    @OneToMany(targetEntity = StoreItem.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "store_id", referencedColumnName = "store_id")
    @OrderBy("name")
    private List<StoreItem> items;

    @OneToMany(targetEntity = Drone.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "store_id", referencedColumnName = "store_id")
    @OrderBy("drone_id")
    private List<Drone> drones;

    @OneToMany(targetEntity = Order.class, orphanRemoval=true, cascade = CascadeType.ALL)
    @JoinColumn(name = "store_id", referencedColumnName = "store_id")
    @OrderBy("order_name")
    private List<Order> orders;

    public Store(String name, int revenue){
        this.setName(name);
        this.setRevenue(revenue);
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addItem(StoreItem item) {
        this.items.add(item);
    }

    public List<StoreItem> getItems() {
        return items;
    }

    public void addDrone(Drone drone){
        this.drones.add(drone);
    }

    public List<Drone> getDrones() {
        return drones;
    }

    public void addOrder(Order order){
        this.orders.add(order);
    }

    public void removeOrder(Order order) {this.orders.remove(order);}

    public List<Order> getOrders() {
        return orders;
    }



}
