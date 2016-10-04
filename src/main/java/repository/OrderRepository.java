package repository;

import domain.Order;

public interface OrderRepository {
    void saveOrder(Order order);
}