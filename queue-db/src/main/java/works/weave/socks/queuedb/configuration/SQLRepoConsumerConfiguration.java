package works.weave.socks.queuedb.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import works.weave.socks.queuedb.controllers.OrderTaskHandler;
import org.springframework.context.annotation.ComponentScan;

@Configuration
//@ComponentScan(basePackages = {"works.weave.socks.queuedb.controllers"})
public class SQLRepoConsumerConfiguration extends RabbitMqConfiguration
{
	protected final String queueName = "sqlrepo-task";

    @Autowired
    private OrderTaskHandler orderTaskHandler;

	@Bean
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate template = new RabbitTemplate(connectionFactory());
		template.setQueue(this.queueName);
        template.setMessageConverter(jsonMessageConverter());
		return template;
	}

    @Bean
	public Queue queueName() {
		return new Queue(this.queueName, false);
	}

	@Bean
	public SimpleMessageListenerContainer listenerContainer() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory());
		container.setQueueNames(this.queueName);
		container.setMessageListener(messageListenerAdapter());

		return container;
	}

    @Bean
    public MessageListenerAdapter messageListenerAdapter() {
    	System.out.println("Teste123");
        return new MessageListenerAdapter(orderTaskHandler, jsonMessageConverter());
    }
}
