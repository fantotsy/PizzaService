package ua.fantotsy.domain;

public class AccumulativeCard {
    private Double balance;

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void increaseAccumulativeCardBalance(double delta){
        balance += delta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccumulativeCard that = (AccumulativeCard) o;

        return balance != null ? balance.equals(that.balance) : that.balance == null;

    }

    @Override
    public int hashCode() {
        return balance != null ? balance.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "AccumulativeCard{" +
                "balance=" + balance +
                '}';
    }
}