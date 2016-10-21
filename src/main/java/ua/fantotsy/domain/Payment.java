package ua.fantotsy.domain;

import ua.fantotsy.domain.discounts.Discount;

import javax.persistence.*;

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

    /*Constructors*/
    public Payment() {

    }

    public Payment(Double initialPrice, Discount appliedDiscount, Double discount, Double totalPrice) {
        this.initialPrice = initialPrice;
        this.appliedDiscount = appliedDiscount;
        this.discount = discount;
        this.totalPrice = totalPrice;
    }

    /*Getters & Setters*/

//    Long getId() {
//        return id;
//    }
//
//    void setId(Long id) {
//        this.id = id;
//    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment)) return false;

        Payment payment = (Payment) o;

        if (Double.compare(payment.initialPrice, initialPrice) != 0) return false;
        if (Double.compare(payment.discount, discount) != 0) return false;
        if (Double.compare(payment.totalPrice, totalPrice) != 0) return false;
        return appliedDiscount != null ? appliedDiscount.equals(payment.appliedDiscount) : payment.appliedDiscount == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(initialPrice);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (appliedDiscount != null ? appliedDiscount.hashCode() : 0);
        temp = Double.doubleToLongBits(discount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(totalPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "\tInitial price: " + initialPrice + " UAH" +
                "\n\tDiscount: " + discount + " UAH (" + appliedDiscount.getName() + ")" +
                "\n\tIN TOTAL: " + totalPrice + " UAH\n";
    }
}