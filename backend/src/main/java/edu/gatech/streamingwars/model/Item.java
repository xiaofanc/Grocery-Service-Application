package edu.gatech.streamingwars.model;

import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = true, length = 50)
    private Long id;

    @Column(nullable = false)
    private String name;

    private int weight;

    public Item(String name, int weight){
        this.setName(name);
        this.setWeight(weight);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

}
