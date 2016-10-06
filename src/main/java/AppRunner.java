import domain.Customer;
import domain.Order;
import infrastructure.context.ApplicationContext;
import infrastructure.context.Context;
import infrastructure.config.JavaConfig;
import services.order.OrderService;

public class AppRunner {
    public static void main(String[] args) {
        System.out.println("Pizza Service");
        Context context = new ApplicationContext(new JavaConfig());

        Customer customer = new Customer();
        OrderService orderService = context.getBean("orderService");
        Order order = orderService.placeNewOrder(customer, 1, 2, 3);
        System.out.println(order);
    }
}