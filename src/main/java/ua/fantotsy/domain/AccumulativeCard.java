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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "balance", nullable = false)
    private double balance;

    /*Constructors*/
    public AccumulativeCard() {

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