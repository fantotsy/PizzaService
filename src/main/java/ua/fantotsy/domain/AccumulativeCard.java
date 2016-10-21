package ua.fantotsy.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "accumulative_cards")
@Component
@Scope(scopeName = "prototype")
public class AccumulativeCard implements Serializable {
    /*Fields*/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "balance", nullable = false)
    private double balance;
    @OneToOne(mappedBy = "accumulativeCard")
    private Customer customer;

    /*Constructors*/
    public AccumulativeCard() {

    }

    public AccumulativeCard(Long id, double balance) {
        this.id = id;
        this.balance = balance;
    }

    /*Methods*/
    void increaseAccumulativeCardBalance(double delta) {
        balance += delta;
    }

    /*Getters & Setters*/
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccumulativeCard)) return false;

        AccumulativeCard that = (AccumulativeCard) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Accumulation card id: " + id +
                "\nAccumulation card balance: " + balance + "\n";
    }
}