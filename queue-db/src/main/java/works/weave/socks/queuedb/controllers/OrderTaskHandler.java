package works.weave.socks.queuedb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import works.weave.socks.sqlrepo.entities.CustomerOrder;
import works.weave.socks.repositories.OrderRepository;

@Component
public class OrderTaskHandler {

	@Autowired
	private OrderRepository repository;
    
	public void handleMessage(CustomerOrder order) {
		System.out.println("Received order task: " + order.getOrderId());
		repository.save(order);
	}
}
