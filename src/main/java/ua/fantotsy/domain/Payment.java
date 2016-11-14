package ua.fantotsy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.context.annotation.Scope;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Component;
import ua.fantotsy.domain.discounts.Discount;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Component
@Scope(scopeName = "prototype")
public class Payment extends ResourceSupport implements Serializable {
    /*Fields*/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long paymentId;
    @Column(name = "initial_price")
    private Double initialPrice;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "applied_discount")
    private Discount appliedDiscount;
    @Column(name = "discount")
    private Double discount;
    @Column(name = "total_price")
    private Double totalPrice;
    @Column(name = "date_time")
    private LocalDateTime dateTime;
    @JsonIgnore
    @OneToOne(mappedBy = "payment", fetch = FetchType.LAZY)
    private Order order;

    /*Constructors*/
    public Payment() {

    }

    public Payment(Double initialPrice, Discount appliedDiscount, Double discount, Double totalPrice, LocalDateTime dateTime) {
        this.initialPrice = initialPrice;
        this.appliedDiscount = appliedDiscount;
        this.discount = discount;
        this.totalPrice = totalPrice;
        this.dateTime = dateTime;
    }

    /*Getters & Setters*/

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Double getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(Double initialPrice) {
        this.initialPrice = initialPrice;
    }

    public Discount getAppliedDiscount() {
        return appliedDiscount;
    }

    public void setAppliedDiscount(Discount appliedDiscount) {
        this.appliedDiscount = appliedDiscount;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        if (initialPrice != null ? !initialPrice.equals(payment.initialPrice) : payment.initialPrice != null)
            return false;
        if (appliedDiscount != null ? !appliedDiscount.equals(payment.appliedDiscount) : payment.appliedDiscount != null)
            return false;
        if (discount != null ? !discount.equals(payment.discount) : payment.discount != null) return false;
        if (totalPrice != null ? !totalPrice.equals(payment.totalPrice) : payment.totalPrice != null) return false;
        return dateTime != null ? dateTime.equals(payment.dateTime) : payment.dateTime == null;
    }

    @Override
    public int hashCode() {
        int result = initialPrice != null ? initialPrice.hashCode() : 0;
        result = 31 * result + (appliedDiscount != null ? appliedDiscount.hashCode() : 0);
        result = 31 * result + (discount != null ? discount.hashCode() : 0);
        result = 31 * result + (totalPrice != null ? totalPrice.hashCode() : 0);
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder info = new StringBuilder();
        if (appliedDiscount != null) {
            info.append("\tInitial price: " + initialPrice + " UAH");
            info.append("\n\tDiscount: " + discount + " UAH (" + appliedDiscount.getName() + ")");
        }
        info.append("\n\tIN TOTAL: " + totalPrice + " UAH\n");
        return info.toString();
    }
}