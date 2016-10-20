package ua.fantotsy.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "pizzas")
@Component
@Scope(scopeName = "prototype")
public class Pizza implements Serializable {
    /*Fields*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false, length = 20, unique = true)
    private String name;
    @Column(name = "price", nullable = false)
    private Double price;
    @Enumerated(EnumType.STRING)
    private PizzaTypes type;

    /*Constructors*/
    protected Pizza() {

    }

    public Pizza(Long id, String name, double price, PizzaTypes type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
    }

    /*Internal Objects*/
    public enum PizzaTypes {
        VEGETARIAN, SEA, MEAT;
    }

    /*Getters & Setters*/
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public PizzaTypes getType() {
        return type;
    }

    public void setType(PizzaTypes type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pizza)) return false;

        Pizza pizza = (Pizza) o;

        return id != null ? id.equals(pizza.id) : pizza.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "\tPizza #" + id +
                "\n\tName: " + name +
                "\n\tType: " + type.name() +
                "\n\tPrice: " + price + " UAH";
    }
}