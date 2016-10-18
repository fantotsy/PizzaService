package ua.fantotsy.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Component
@Scope(scopeName = "prototype")
public class Pizza implements Serializable {
    /*Fields*/
    @Id
    private Long id;
    private String name;
    private double price;
    private PizzaTypes type;

    /*Constructors*/
    public Pizza() {

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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