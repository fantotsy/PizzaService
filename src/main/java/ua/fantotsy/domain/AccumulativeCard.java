package ua.fantotsy.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "prototype")
public class AccumulativeCard {
    /*Fields*/
    private long id;
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