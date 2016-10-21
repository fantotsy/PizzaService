package ua.fantotsy.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "addresses")
@Component
@Scope(scopeName = "prototype")
public class Address implements Serializable {
    /*Fields*/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "city", nullable = false, length = 20)
    private String city;
    @Column(name = "street", nullable = false, length = 20)
    private String street;
    @OneToMany(mappedBy = "address")
    private List<Customer> customers;

    /*Constructors*/
    public Address() {

    }

    public Address(String city, String street) {
        this.city = city;
        this.street = street;
    }

    /*Getters & Setters*/
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;

        Address address = (Address) o;

        return id == address.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "\t\tCity: " + city +
                "\n\t\tStreet: " + street + "\n";
    }
}