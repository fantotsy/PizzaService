package ua.fantotsy.domain;

import ua.fantotsy.domain.discounts.Discount;

class Payment {
    private double initialPrice;
    private Discount appliedDiscount;
    private double discount;
    private double totalPrice;

    double getInitialPrice() {
        return initialPrice;
    }

    void setInitialPrice(double initialPrice) {
        this.initialPrice = initialPrice;
    }

    public Discount getAppliedDiscount() {
        return appliedDiscount;
    }

    public void setAppliedDiscount(Discount appliedDiscount) {
        this.appliedDiscount = appliedDiscount;
    }

    double getDiscount() {
        return discount;
    }

    void setDiscount(double discount) {
        this.discount = discount;
    }

    double getTotalPrice() {
        return totalPrice;
    }

    void setTotalPrice(double totalPrice) {
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