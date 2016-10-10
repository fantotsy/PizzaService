package ua.fantotsy.domain;

public class Customer {
    /*Fields*/
    private Long id;
    private String name;
    private Address address;
    private AccumulativeCard accumulativeCard;

    /*Constructors*/
    public Customer(String name, Address address, boolean hasAccumulativeCard) {
        this.name = name;
        this.address = address;
        if (hasAccumulativeCard) {
            accumulativeCard = new AccumulativeCard();
        }
    }

    /*Methods*/
    public double getCardBalance() {
        if (hasAccumulativeCard()) {
            return accumulativeCard.getBalance();
        }
        throw new RuntimeException("This customer has no accumulative card.");
    }

    public boolean hasAccumulativeCard() {
        return (accumulativeCard != null);
    }

    public void increaseAccumulativeCardBalance(double delta) {
        accumulativeCard.increaseAccumulativeCardBalance(delta);
    }

    /*Getters & Setters*/
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (id != null ? !id.equals(customer.id) : customer.id != null) return false;
        if (name != null ? !name.equals(customer.name) : customer.name != null) return false;
        return address != null ? address.equals(customer.address) : customer.address == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                '}';
    }
}