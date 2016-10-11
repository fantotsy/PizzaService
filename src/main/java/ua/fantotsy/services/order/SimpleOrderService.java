package ua.fantotsy.services.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import ua.fantotsy.domain.Customer;
import ua.fantotsy.domain.Order;
import ua.fantotsy.domain.Pizza;
import ua.fantotsy.infrastructure.annotations.BenchMark;
import ua.fantotsy.repository.order.OrderRepository;
import ua.fantotsy.services.customer.CustomerService;
import ua.fantotsy.services.pizza.PizzaService;

import java.util.ArrayList;
import java.util.List;

public class SimpleOrderService implements OrderService {
    /*Fields*/
    private final OrderRepository orderRepository;
    private final PizzaService pizzaService;
    private final CustomerService customerService;
    private ApplicationContext applicationContext;

    /*Constructors*/
    @Autowired
    public SimpleOrderService(OrderRepository orderRepository, PizzaService pizzaService, CustomerService customerService) {
        this.orderRepository = orderRepository;
        this.pizzaService = pizzaService;
        this.customerService = customerService;
    }

    /*Public Methods*/
    @Override
    @BenchMark(value = true)
    public Order placeNewOrder(Customer customer, Integer... pizzasId) {
        if (!isAllowedAmountOfPizzas(pizzasId)) {
            throw new RuntimeException("Not allowed amount of pizzas!");
        } else {
            List<Pizza> pizzas = new ArrayList<>();
            for (int id : pizzasId) {
                pizzas.add(getPizzaById(id));
            }
            Order newOrder = createNewOrder();
            newOrder.setCustomer(customer);
            newOrder.setOrder(pizzas);
            newOrder.countTotalPrice();
            orderRepository.saveOrder(newOrder);
            return newOrder;
        }
    }

    @Override
    public Pizza getPizzaById(long id) {
        return pizzaService.getPizzaById(id);
    }

    @Override
    public Customer getCustomerById(long id) {
        return customerService.getCustomerById(id);
    }

    @Override
    public void saveOrder(Order newOrder) {
        orderRepository.saveOrder(newOrder);
    }

    @Override
    public int getNumberOfOrders() {
        return orderRepository.getNumberOfOrders();
    }

    @Override
    public void addNewPizza(Pizza newPizza) {
        pizzaService.addNewPizza(newPizza);
    }

    @Override
    public void addNewCustomer(Customer newCustomer) {
        customerService.addNewCustomer(newCustomer);
    }

    @Override
    public void payOrderById(long id) {
        orderRepository.payOrderById(id);
    }

    @Override
    public void cancelOrderById(long id) {
        orderRepository.cancelOrderById(id);
    }

    @Override
    public double getTotalOrderPriceById(long id) {
        return orderRepository.getOrderById(id).getTotalPrice();
    }

    /*Protected & Private Methods*/
    protected Order createNewOrder() {
        throw new IllegalStateException();
    }

    private boolean isAllowedAmountOfPizzas(Integer... pizzasId) {
        return ((pizzasId.length >= 1) && (pizzasId.length <= 10));
    }
}