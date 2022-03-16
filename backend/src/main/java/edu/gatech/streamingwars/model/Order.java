package edu.gatech.streamingwars.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, updatable = false, name = "order_name")
    private String orderName;
    private String droneId;
    private String customerAccount;

    @OneToMany(targetEntity = OrderItem.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @OrderBy("name")
    private List<OrderItem> items;

    private int totalCost = 0;
    private int totalWeight = 0;
    private int droneCapacity;
    private int customerCredit;

    public Order(String orderName, String droneId, String customerAccount, int droneCapacity, int customerCredit){
        this.setOrderName(orderName);
        this.setDroneId(droneId);
        this.setCustomerAccount(customerAccount);
        this.setDroneCapacity(droneCapacity);
        this.setCustomerCredit(customerCredit);
    }

    public Long getId() {
        return id;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setDroneId(String droneId) {
        this.droneId = droneId;
    }

    public String getDroneId() {
        return droneId;
    }

    public void setCustomerAccount(String customerAccount) {
        this.customerAccount = customerAccount;
    }

    public String getCustomerAccount() {
        return customerAccount;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalWeight(int totalWeight) {
        this.totalWeight = totalWeight;
    }

    public int getTotalWeight() {
        return totalWeight;
    }

    public void setDroneCapacity(int droneCapacity) {
        this.droneCapacity = droneCapacity;
    }

    public int getDroneCapacity() {
        return droneCapacity;
    }

    public void setCustomerCredit(int customerCredit) {
        this.customerCredit = customerCredit;
    }

    public int getCustomerCredit() {
        return customerCredit;
    }

    public List<OrderItem> getItems() {
        return items;
    }

}
