package works.weave.socks.queuedb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import works.weave.socks.shipping.entities.Shipment;

@Component
public class ShippingTaskHandler {

	@Autowired
	DockerSpawner docker;

	public void handleMessage(ConsumerOrder order) {
		System.out.println("Received order task: " + order.getName());
		//docker.init();
		//docker.spawn();
	}
}
