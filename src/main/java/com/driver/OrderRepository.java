package com.driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.stereotype.Repository;


@Repository
public class OrderRepository {
	HashMap<String,Order> ordersDb=new HashMap<>();
	HashMap<String,String> orderPartnerDb=new HashMap<>();
	HashMap<String,DeliveryPartner> partnerDb=new HashMap<>();
	HashMap<String,List<Order>> partnerOrdersDb=new HashMap<>();
	
	public void addOrder(Order order){
		String id=order.getId();
		ordersDb.put(id,order);
	}
	
	public void addPartner(String id){
		DeliveryPartner p=new DeliveryPartner(id);
		partnerDb.put(id, p);
		
	}
	
	public void addOrderPartnerPair(String oId,String pId) {
		Order order=ordersDb.get(oId);
		
		
		if(!partnerDb.containsKey(pId)) addPartner(pId);
		orderPartnerDb.put(oId,pId);
		
		if(partnerOrdersDb.containsKey(pId)) {
			partnerOrdersDb.get(pId).add(order);
		}else {
			partnerOrdersDb.put(pId, new ArrayList<>());
			partnerOrdersDb.get(pId).add(order);
		}
		
		partnerDb.get(pId).setNumberOfOrders(partnerOrdersDb.get(pId).size());
	}
	
	public Order getOrderById(String id) {
		if(ordersDb.containsKey(id)) {
			return ordersDb.get(id);
		}
		return null;
	}
	
	public DeliveryPartner getPartnerById(String id) {
		if(partnerDb.containsKey(id)) {
			return partnerDb.get(id);
		}
		return null;
	}
	
	public int getOrderCountByPartnerId(String id) {
		return partnerDb.get(id).getNumberOfOrders();
	}
	
	public List<String> getOrdersByPartnerId(String id){
		if(partnerOrdersDb.containsKey(id)) {
			List<String> l=new ArrayList<>();
			for(Order order:partnerOrdersDb.get(id)) {
				l.add(order.getId());
			}
			return l;
		}
		return null;
		
	}
	
	public List<String> getAllOrders(){
		List<String> l=new ArrayList<>();
		l.addAll(ordersDb.keySet());
		
		return l;
		
	}
	
	public int getCountOfUnassignedOrders(){
		int c=0;
		for(String id:partnerOrdersDb.keySet()) {
			c+=partnerOrdersDb.get(id).size();
		}
		return ordersDb.size()-c;
	}
	
	public int getOrdersLeftAfterGivenTimeByPartnerId(String time,String id){
		
		int hourIntoMins=Integer.parseInt(time.substring(0,2))*60;
    	int mins=Integer.parseInt(time.substring(3,time.length()));
    	int calcTime=hourIntoMins+mins;
    	
    	int ans=0;
    	
    	if(partnerOrdersDb.containsKey(id)){
    		
    		for(Order order:partnerOrdersDb.get(id)) {
    			if(order.getDeliveryTime()>calcTime) ans++;
    		}
    		
    		
    	}
    	return ans;
    	
	}
	
	public String getLastDeliveryTimeByPartnerId(String id) {
		if(!partnerOrdersDb.containsKey(id) || partnerOrdersDb.get(id).size()==0) return null;
		
		int lastDeliveryTime=-1;
		for(Order order:partnerOrdersDb.get(id)) {
			lastDeliveryTime=Math.max(lastDeliveryTime,order.getDeliveryTime());
		}
		String hour=String.valueOf(lastDeliveryTime/60);
		String mins=String.valueOf(lastDeliveryTime%60);
		if(hour.length()<2) hour="0"+hour;
		if(mins.length()<2) mins="0"+mins;
		String result=hour+":"+mins;
		
		return result;
				
	}
	
	public String deletePartnerById(String pId){
		
		
		Iterator<Entry<String, String>> iterator = orderPartnerDb.entrySet().iterator();
		
	    while (iterator.hasNext()) {
	        Entry<String, String> entry= iterator.next();
	
	        if (pId == entry.getValue()) iterator.remove();
	 
	    }
	    
	    if(partnerOrdersDb.containsKey(pId)) partnerOrdersDb.remove(pId);
	    if(partnerDb.containsKey(pId)) partnerDb.remove(pId);
		
		return pId;
	}
	
	public String deleteOrderById(String oId){
		
		if(ordersDb.containsKey(oId)) ordersDb.remove(oId);
		String pId=null;
		if(orderPartnerDb.containsKey(oId)) {
			 pId=orderPartnerDb.get(oId);
			orderPartnerDb.remove(oId);
		}
		if(pId!=null && partnerOrdersDb.containsKey(pId) && partnerOrdersDb.get(pId).size()!=0){
			Order o=null;
			for(Order order:partnerOrdersDb.get(pId)) {
				if(order.getId()==oId) {
					o=order;
					break;
				}
				
			}
			
			if(o!=null) partnerOrdersDb.get(pId).remove(o);
		}
		
		
		
		return oId;
		
	}

}
