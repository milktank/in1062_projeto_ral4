package works.weave.socks.queuedb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
//import works.weave.socks.queuedb.configuration.PersistenceContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import works.weave.socks.queuedb.configuration.SQLRepoConsumerConfiguration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import works.weave.socks.queuedb.controllers.OrderTaskHandler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import works.weave.socks.sqlrepo.entities.CustomerOrder;

@SpringBootApplication
@EnableAutoConfiguration
@EntityScan(basePackages = {"works.weave.socks.sqlrepo.entities"} )
@EnableJpaRepositories(basePackages = {"works.weave.socks.repositories"})
@ComponentScan(basePackages = {"works.weave.socks.repositories", "works.weave.socks.queuedb.controllers"})
public class QueueDBApplication {

	protected final String queueName = "sqlrepo-task";

    @Autowired
    private OrderTaskHandler orderTaskHandler;

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Bean
    public ConnectionFactory connectionFactory()
    {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host);
        connectionFactory.setCloseTimeout(5000);
        connectionFactory.setConnectionTimeout(5000);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin()
    {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public MessageConverter jsonMessageConverter()
    {
        final Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setClassMapper(classMapper());
        return converter;
    }

    @Bean
    public DefaultClassMapper classMapper()
    {
        DefaultClassMapper typeMapper = new DefaultClassMapper();
        typeMapper.setDefaultType(CustomerOrder.class);
        return typeMapper;
    }

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("sqlrepo-task-exchange");
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(queueName);
    }

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
        return new MessageListenerAdapter(orderTaskHandler, jsonMessageConverter());
    }

    public static void main(String[] args) throws InterruptedException {
		//AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(SQLRepoConsumerConfiguration.class);
		SpringApplication.run(QueueDBApplication.class, args);
	}
}
