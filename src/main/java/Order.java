import java.util.List;

public class Order {
    private Long id;
    private List<Pizza> order;
    private Customer customer;

    public Order(Long id, Customer customer, List<Pizza> order) {
        this.id = id;
        this.order = order;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Pizza> getOrder() {
        return order;
    }

    public void setOrder(List<Pizza> order) {
        this.order = order;
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

        Order order1 = (Order) o;

        if (id != null ? !id.equals(order1.id) : order1.id != null) return false;
        if (order != null ? !order.equals(order1.order) : order1.order != null) return false;
        return customer != null ? customer.equals(order1.customer) : order1.customer == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + (customer != null ? customer.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", order=" + order +
                ", customer=" + customer +
                '}';
    }
}