package ua.fantotsy.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "pizzas")
@Component
@Scope(scopeName = "prototype")
@NamedQueries({
        @NamedQuery(name = "Pizza.findByName", query = "SELECT p FROM Pizza p WHERE p.name=:name"),
})
public class Pizza implements Serializable {
    /*Fields*/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name", nullable = false, length = 20)
    private String name;
    @Column(name = "price", nullable = false)
    private Double price;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private PizzaType type;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private PizzaStatus status;

    /*Constructors*/
    protected Pizza() {
        status = PizzaStatus.AVAILABLE;
    }

    public Pizza(String name, double price, PizzaType type) {
        this();
        this.name = name;
        this.price = price;
        this.type = type;
    }

    /*Internal Objects*/
    public enum PizzaType {
        VEGETARIAN, SEA, MEAT;
    }

    public enum PizzaStatus {
        AVAILABLE, NOT_AVAILABLE, CANCELED;
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

    public PizzaType getType() {
        return type;
    }

    public void setType(PizzaType type) {
        this.type = type;
    }

    public PizzaStatus getStatus() {
        return status;
    }

    public void setStatus(PizzaStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pizza pizza = (Pizza) o;
        if (name != null ? !name.equals(pizza.name) : pizza.name != null) return false;
        if (price != null ? !price.equals(pizza.price) : pizza.price != null) return false;
        return type == pizza.type;
    }

    @Override
    public int hashCode() {
        int result = (name != null ? name.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "\tPizza #" + id +
                "\n\tName: " + name +
                "\n\tType: " + type.name() +
                "\n\tPrice: " + price + " UAH";
    }
}