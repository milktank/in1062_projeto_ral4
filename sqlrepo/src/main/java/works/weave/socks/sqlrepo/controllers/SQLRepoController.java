package works.weave.socks.sqlrepo.controllers;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.ChannelCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import works.weave.socks.sqlrepo.entities.HealthCheck;
import works.weave.socks.sqlrepo.entities.Shipment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SQLRepoController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    //@RequestMapping(value = "/search/{id}", method = RequestMethod.GET)
    //public String getOrderById(@PathVariable String id) {
    //    return "GET Order Resource with id: " + id;
    //}

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public
    @ResponseBody
    CustomerOrder saveOrder(@RequestBody CustomerOrder order) {
        System.out.println("Adding order to queue...");
        try {
            rabbitTemplate.convertAndSend("sqlrepo-task", order);
        } catch (Exception e) {
            System.out.println("Unable to add to queue (the queue is probably down). Accepting anyway. Don't do this " +
                    "for real!");
        }
        return order;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, path = "/health")
    public
    @ResponseBody
    Map<String, List<HealthCheck>> getHealth() {
        Map<String, List<HealthCheck>> map = new HashMap<String, List<HealthCheck>>();
        List<HealthCheck> healthChecks = new ArrayList<HealthCheck>();
        Date dateNow = Calendar.getInstance().getTime();

        HealthCheck rabbitmq = new HealthCheck("sqlrepo-rabbitmq", "OK", dateNow);
        HealthCheck app = new HealthCheck("sqlrepo", "OK", dateNow);

        try {
            this.rabbitTemplate.execute(new ChannelCallback<String>() {
                @Override
                public String doInRabbit(Channel channel) throws Exception {
                    Map<String, Object> serverProperties = channel.getConnection().getServerProperties();
                    return serverProperties.get("version").toString();
                }
            });
        } catch ( AmqpException e ) {
            rabbitmq.setStatus("err");
        }

        healthChecks.add(rabbitmq);
        healthChecks.add(app);

        map.put("health", healthChecks);
        return map;
    }
}
