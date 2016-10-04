public class AppRunner {
    public static void main(String[] args) {
        Customer customer = new Customer(1L, "Vasya");
        Order order;
        SimpleOrderService simpleOrderService = new SimpleOrderService();
        order = simpleOrderService.placeNewOrder(customer, 1, 2, 3);

        System.out.println(order);
    }
}