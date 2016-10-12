package ua.fantotsy.domain;

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
    public double getBalance() {
        return balance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccumulativeCard that = (AccumulativeCard) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "AccumulativeCard{" +
                "id=" + id +
                ", balance=" + balance +
                '}';
    }
}