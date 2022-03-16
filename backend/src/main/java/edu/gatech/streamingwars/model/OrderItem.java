package edu.gatech.streamingwars.model;

import lombok.NoArgsConstructor;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
public class OrderItem extends Item{

    private int quantity;
    public OrderItem(String name, int weight) {
        super(name, weight);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
