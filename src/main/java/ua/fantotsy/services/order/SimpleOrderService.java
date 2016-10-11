package ua.fantotsy.services.order;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import ua.fantotsy.domain.Customer;
import ua.fantotsy.domain.Order;
import ua.fantotsy.domain.Pizza;
import ua.fantotsy.repository.order.OrderRepository;
import ua.fantotsy.services.customer.CustomerService;
import ua.fantotsy.services.pizza.PizzaService;

import java.util.ArrayList;
import java.util.List;

public class SimpleOrderService implements OrderService, ApplicationContextAware {
    /*Fields*/
    private final OrderRepository orderRepository;
    private final PizzaService pizzaService;
    private final CustomerService customerService;
    private ApplicationContext applicationContext;

    /*Constructors*/
    public SimpleOrderService(OrderRepository orderRepository, PizzaService pizzaService, CustomerService customerService) {
        this.orderRepository = orderRepository;
        this.pizzaService = pizzaService;
        this.customerService = customerService;
    }

    /*Public Methods*/
    @Override
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
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
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

    /*Private Methods*/
    private Order createNewOrder() {
        return (Order) applicationContext.getBean("order");
    }

    private boolean isAllowedAmountOfPizzas(Integer... pizzasId) {
        return ((pizzasId.length >= 1) && (pizzasId.length <= 10));
    }
}