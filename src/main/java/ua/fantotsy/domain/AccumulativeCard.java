package ua.fantotsy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.context.annotation.Scope;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "accumulative_cards")
@Component
@Scope(scopeName = "prototype")
@NamedQueries({
        @NamedQuery(name = "AccumulativeCard.getMaxCardNumber",
                query = "SELECT MAX(ac.number) FROM AccumulativeCard ac"),
})
public class AccumulativeCard extends ResourceSupport implements Serializable {
    /*Fields*/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long accumulativeCardId;
    @Column(name = "number", nullable = false, length = 5)
    private Long number;
    @Column(name = "balance", nullable = false)
    private Double balance;
    @JsonIgnore
    @OneToOne(mappedBy = "accumulativeCard", fetch = FetchType.LAZY)
    private Customer customer;

    /*Constructors*/
    public AccumulativeCard() {
        balance = 0.0;
        number = 10000L;
    }

    public AccumulativeCard(Long number) {
        this();
        this.number = number;
    }

    /*Methods*/
    void increaseAccumulativeCardBalance(Double delta) {
        balance += delta;
    }

    /*Getters & Setters*/
    public Long getAccumulativeCardId() {
        return accumulativeCardId;
    }

    public void setAccumulativeCardId(Long accumulativeCardId) {
        this.accumulativeCardId = accumulativeCardId;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
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
        return number != null ? number.equals(that.number) : that.number == null;
    }

    @Override
    public int hashCode() {
        return number != null ? number.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Accumulative card id: " + accumulativeCardId +
                "\nAccumulative card balance: " + balance + "\n";
    }
}