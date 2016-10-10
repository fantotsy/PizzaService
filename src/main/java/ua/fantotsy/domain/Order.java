package ua.fantotsy.domain;

import ua.fantotsy.infrastructure.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private Long id;
    private List<Pizza> order;
    private Customer customer;
    private Double totalPrice;
    private Status status;

    private static int PERCENTAGE_OF_PIZZA_PRICE_ON_PIZZA_DISCOUNT = 30;
    private static int PERCENTAGE_OF_CARD_BALANCE_ON_ORDER_DISCOUNT = 10;
    private static int MAX_PERCENTAGE_OF_ORDER_PRICE_FOR_CARD_DISCOUNT = 30;
    private static int MAX_AMOUNT_OF_PIZZAS_FOR_DISCOUNT = 4;

    public Order(Customer customer, List<Pizza> order) {
        this.order = order;
        this.customer = customer;
        status = Status.NEW;
    }

    private enum Status {
        NEW {
            public Status nextStatus() {
                return IN_PROGRESS;
            }

            public Status previousStatus() {
                throw new RuntimeException("Previous status for NEW does not exist.");
            }
        },
        IN_PROGRESS {
            public Status nextStatus() {
                return DONE;
            }

            public Status previousStatus() {
                return CANCELED;
            }
        },
        CANCELED {
            public Status nextStatus() {
                throw new RuntimeException("Next status for CANCELED does not exist.");
            }

            public Status previousStatus() {
                throw new RuntimeException("Previous status for CANCELED does not exist.");
            }
        },
        DONE {
            public Status nextStatus() {
                throw new RuntimeException("Next status for DONE does not exist.");
            }

            public Status previousStatus() {
                throw new RuntimeException("Previous status for DONE does not exist.");
            }
        };

        public abstract Status nextStatus();

        public abstract Status previousStatus();
    }

    public void setStatus(boolean isNextStatus) {
        if (isNextStatus) {
            status = status.nextStatus();
        } else {
            status = status.previousStatus();
        }
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void countTotalPrice() {
        double result = 0.0;
        if (isMoreThanFourPizzasInOrder()) {
            reducePizzaPrice(getTheMostExpensivePizza(), PERCENTAGE_OF_PIZZA_PRICE_ON_PIZZA_DISCOUNT);
        }
        for (Pizza pizza : order) {
            result += pizza.getPrice();
        }
        if (customer.hasAccumulativeCard()) {
            double percentageOfCardBalance =
                    Utils.getPercentageOfNumber(customer.getCardBalance(), PERCENTAGE_OF_CARD_BALANCE_ON_ORDER_DISCOUNT);
            double percentageOfOrderPrice =
                    Utils.getPercentageOfNumber(result, MAX_PERCENTAGE_OF_ORDER_PRICE_FOR_CARD_DISCOUNT);
            if (percentageOfCardBalance <= percentageOfOrderPrice) {
                result -= percentageOfCardBalance;
            } else {
                result -= percentageOfOrderPrice;
            }
        }
        totalPrice = result;
    }

    private boolean isMoreThanFourPizzasInOrder() {
        return (order.size() > MAX_AMOUNT_OF_PIZZAS_FOR_DISCOUNT);
    }

    public void pay() {
        if (customer.hasAccumulativeCard()) {
            customer.increaseAccumulativeCardBalance(totalPrice);
        }
    }

    private List<Pizza> getTheMostExpensivePizza() {
        if (isEmpty()) {
            throw new RuntimeException("Order is empty.");
        } else {
            List<Pizza> result = new ArrayList<>();
            double maxPrice = order.get(0).getPrice();
            for (Pizza pizza : order) {
                if (pizza.getPrice() < maxPrice) {
                    maxPrice = pizza.getPrice();
                }
            }
            for (Pizza pizza : order) {
                if (pizza.getPrice() == maxPrice) {
                    result.add(pizza);
                }
            }
            return result;
        }
    }

    private void reducePizzaPrice(List<Pizza> pizzas, int percentage) {
        if (!Utils.isAllowedPercentage(percentage)) {
            throw new RuntimeException("Such percentage is not allowed.");
        } else {
            double price = pizzas.get(0).getPrice();
            double discount = Utils.getPercentageOfNumber(price, percentage);
            double reducedPrice = price - discount;
            for (Pizza pizza : pizzas) {
                pizza.setPrice(reducedPrice);
            }
        }
    }

    private boolean isEmpty() {
        return (order.size() == 0);
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
        return "ua.fantotsy.domain.Order{" +
                "id=" + id +
                ", order=" + order +
                ", customer=" + customer +
                '}';
    }
}