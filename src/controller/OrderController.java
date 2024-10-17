package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Order;
import repository.OrderRepository;

public class OrderController {
    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Guest submits an order
    public void submitOrder(String guestName, int tableNumber, int kioskID, List<Integer> drinkIDs, List<Integer> quantities) {
        Order order = new Order(guestName, tableNumber, kioskID, "open");
        
        // Add drinks to the order
        try {
        	int orderId = orderRepository.createOrder(order);
			if (orderId != -1) orderRepository.addDrinksToOrder(orderId, drinkIDs, quantities);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    // Bartender selects an order for processing
    public boolean selectOrderForProcessing(int orderID, int bartenderID) {
        Order order;
		try {
			order = orderRepository.getOrderById(orderID);
			
			if (order != null && "open".equals(order.getOrderStatus())) {
	            // Update the order status to 'processing'
	            order.setOrderStatus("processing");
	            order.setBartenderID(bartenderID);
	            orderRepository.updateOrder(order);
	            return true;
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
        return false;  // If the order is already being processed
    }

    // Marks an order as served
    public void markOrderAsServed(int orderID) {
        Order order;
		try {
			order = orderRepository.getOrderById(orderID);
	        if (order != null) {
	            order.setOrderStatus("completed");
	            orderRepository.updateOrder(order);
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    // Fetch all open orders for bartenders
    public List<Order> getOpenOrders() {
    	List<Order> orders = new ArrayList<>();
        try {
        	orders = orderRepository.getOrdersByStatus("open");
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return orders;
    }
}
