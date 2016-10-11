package ua.fantotsy.services.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import ua.fantotsy.domain.Customer;
import ua.fantotsy.domain.Order;
import ua.fantotsy.domain.Pizza;
import ua.fantotsy.domain.discounts.Discount;
import ua.fantotsy.infrastructure.annotations.BenchMark;
import ua.fantotsy.repository.discount.DiscountRepository;
import ua.fantotsy.repository.order.OrderRepository;
import ua.fantotsy.services.customer.CustomerService;
import ua.fantotsy.services.discount.DiscountService;
import ua.fantotsy.services.pizza.PizzaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SimpleOrderService implements OrderService {
    /*Fields*/
    private final OrderRepository orderRepository;
    private final PizzaService pizzaService;
    private final CustomerService customerService;
    private final DiscountService discountService;
    private ApplicationContext applicationContext;

    /*Constructors*/
    @Autowired
    public SimpleOrderService(OrderRepository orderRepository, PizzaService pizzaService,
                              CustomerService customerService, DiscountService discountService) {
        this.orderRepository = orderRepository;
        this.pizzaService = pizzaService;
        this.customerService = customerService;
        this.discountService = discountService;
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
            newOrder.setActiveDiscounts(getActiveDiscounts());
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
    public Set<Discount> getDiscounts() {
        return discountService.getDiscounts();
    }

    @Override
    public Set<Discount> getActiveDiscounts() {
        Set<Discount> result = getDiscounts();
        for (Discount discount : result) {
            if (discount.getState().equals(Discount.DiscountState.INACTIVE)) {
                result.remove(discount);
            }
        }
        return result;
    }

    @Override
    public Customer getCustomerById(long id) {
        return customerService.getCustomerById(id);
    }

    @Override
    public Discount getDiscountByName(String name) {
        return discountService.getDiscountByName(name);
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
    public void addNewDiscount(Discount discount) {
        discountService.addNewDiscount(discount);
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