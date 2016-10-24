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
    @Column(name = "number", nullable = false, unique = true, length = 16)
    private String number;
    @Column(name = "balance", nullable = false)
    private Double balance;
    @OneToOne(mappedBy = "accumulativeCard")
    private Customer customer;

    /*Constructors*/
    public AccumulativeCard() {
        balance = 0.0;
    }

    public AccumulativeCard(String number) {
        this();
        this.number = number;
    }

    /*Methods*/
    void increaseAccumulativeCardBalance(Double delta) {
        balance += delta;
    }

    /*Getters & Setters*/
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
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
        if (o == null || getClass() != o.getClass()) return false;

        AccumulativeCard that = (AccumulativeCard) o;

        if (Double.compare(that.balance, balance) != 0) return false;
        return number != null ? number.equals(that.number) : that.number == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = number != null ? number.hashCode() : 0;
        temp = Double.doubleToLongBits(balance);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Accumulation card id: " + id +
                "\nAccumulation card balance: " + balance + "\n";
    }
}