package com.driver;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
	
//	@Autowired
	OrderRepository orderRepository=new OrderRepository();
	
	public void addOrder(Order order){
		orderRepository.addOrder(order);
	}
	
	public void addPartner(String id){
		orderRepository.addPartner(id);
		
	}
	
	public void addOrderPartnerPair(String oId,String pId) {
		orderRepository.addOrderPartnerPair(oId, pId);
	}
	
	public Order getOrderById(String id) {
		
		return orderRepository.getOrderById(id);
	}
	
	public DeliveryPartner getPartnerById(String id) {
		return orderRepository.getPartnerById(id);
	}
	
	public int getOrderCountByPartnerId(String id) {
		return orderRepository.getOrderCountByPartnerId(id);
	}
	
	public List<String> getOrdersByPartnerId(String id){
		return orderRepository.getOrdersByPartnerId(id);
	}
	
	public List<String> getAllOrders(){
		return orderRepository.getAllOrders();
	}
	
	public int getCountOfUnassignedOrders() {
		return orderRepository.getCountOfUnassignedOrders();
	}
	
	public int getOrdersLeftAfterGivenTimeByPartnerId(String time,String id) {
		return orderRepository.getOrdersLeftAfterGivenTimeByPartnerId(time, id);
	}
	
	public String getLastDeliveryTimeByPartnerId(String id) {
		return orderRepository.getLastDeliveryTimeByPartnerId(id);
	}
	
	public String deletePartnerById(String pId) {
		return orderRepository.deletePartnerById(pId);
	}
	
	public String deleteOrderById(String oId) {
		return orderRepository.deleteOrderById(oId);
	}

}
