package ua.fantotsy.domain;

class Payment {
    private double initialPrice;
    private double discount;
    private double totalPrice;

    double getInitialPrice() {
        return initialPrice;
    }

    void setInitialPrice(double initialPrice) {
        this.initialPrice = initialPrice;
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
}