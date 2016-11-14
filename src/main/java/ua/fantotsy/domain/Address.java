package ua.fantotsy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.context.annotation.Scope;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "addresses")
@Component
@Scope(scopeName = "prototype")
public class Address extends ResourceSupport implements Serializable {
    /*Fields*/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long addressId;
    @Column(name = "city", nullable = false, length = 20)
    private String city;
    @Column(name = "street", nullable = false, length = 20)
    private String street;
    @JsonIgnore
    @OneToMany(mappedBy = "address", fetch = FetchType.LAZY)
    private List<Customer> customers;
    @JsonIgnore
    @OneToMany(mappedBy = "address", fetch = FetchType.LAZY)
    private List<Order> orders;

    /*Constructors*/
    public Address() {

    }

    public Address(String city, String street) {
        this.city = city;
        this.street = street;
    }

    /*Getters & Setters*/
    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        if (city != null ? !city.equals(address.city) : address.city != null) return false;
        return street != null ? street.equals(address.street) : address.street == null;
    }

    @Override
    public int hashCode() {
        int result = city != null ? city.hashCode() : 0;
        result = 31 * result + (street != null ? street.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "\t\tCity: " + city +
                "\n\t\tStreet: " + street + "\n";
    }
}