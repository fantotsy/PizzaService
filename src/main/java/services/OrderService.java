package services;

import domain.Customer;
import domain.Order;

public interface OrderService {
    Order placeNewOrder(Customer customer, Integer... pizzasId);
}