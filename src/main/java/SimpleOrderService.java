import java.util.ArrayList;
import java.util.List;

public class SimpleOrderService {
    private List<Pizza> pizzas;
    private List<Order> orders;

    public SimpleOrderService() {
        pizzas = new ArrayList<Pizza>() {{
            add(new Pizza(1L, "First", 100.0, Pizza.PizzaTypes.Vegetarian));
            add(new Pizza(2L, "Second", 200.0, Pizza.PizzaTypes.Sea));
            add(new Pizza(3L, "Third", 300.0, Pizza.PizzaTypes.Meat));
            add(new Pizza(4L, "Third", 400.0, Pizza.PizzaTypes.Meat));
        }};

        orders = new ArrayList<>();
    }

    public Order placeNewOrder(Customer customer, Integer... pizzasId) {
        List<Pizza> pizzas = new ArrayList<>();
        for (int id : pizzasId) {
            pizzas.add(getPizzaById(id));
        }
        Order newOrder = new Order(1L, customer, pizzas);
        return newOrder;
    }

    private Pizza getPizzaById(int id) {
        for (Pizza pizza : pizzas) {
            if (pizza.getId() == id) {
                return pizza;
            }
        }
        throw new IllegalArgumentException();
    }

    private void saveOrder(Order order) {
        orders.add(order);
    }
}