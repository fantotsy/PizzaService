package ua.fantotsy.domain;

import ua.fantotsy.domain.discounts.Discount;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Embeddable
@Table(name = "payments")
class Payment {
    /*Fields*/
    @Column(name = "initial_price")
    private Double initialPrice;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "discount_name")
    private Discount appliedDiscount;
    @Column(name = "discount")
    private Double discount;
    @Column(name = "total_price")
    private Double totalPrice;
    @Column(name = "date_time")
    private LocalDateTime dateTime;

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

    Double getInitialPrice() {
        return initialPrice;
    }

    void setInitialPrice(Double initialPrice) {
        this.initialPrice = initialPrice;
    }

    public Discount getAppliedDiscount() {
        return appliedDiscount;
    }

    public void setAppliedDiscount(Discount appliedDiscount) {
        this.appliedDiscount = appliedDiscount;
    }

    Double getDiscount() {
        return discount;
    }

    void setDiscount(Double discount) {
        this.discount = discount;
    }

    Double getTotalPrice() {
        return totalPrice;
    }

    void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
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
        return "\tInitial price: " + initialPrice + " UAH" +
                "\n\tDiscount: " + discount + " UAH (" + appliedDiscount.getName() + ")" +
                "\n\tIN TOTAL: " + totalPrice + " UAH\n";
    }
}