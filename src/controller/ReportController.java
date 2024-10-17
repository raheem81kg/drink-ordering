package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Order;
import repository.OrderRepository;

public class ReportController {
    private final OrderRepository orderRepository;

    public ReportController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Get all orders for a specific date
    public List<Order> getOrdersForDate(String date) {
    	List<Order> orders = new ArrayList<>();
        try {
        	orders = orderRepository.getOrdersForDate(date);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return orders;
    }

    // Get orders between two dates
    public List<Order> getOrdersBetweenDates(String startDate, String endDate) {
    	List<Order> orders = new ArrayList<>();
        try {
        	orders = orderRepository.getOrdersBetweenDates(startDate, endDate);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return orders;
    }
}
