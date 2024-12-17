package edu.utfpr.estoque;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter converter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter);
        return rabbitTemplate;
    }

    @Bean
    public Queue pedidosCriados3Queue() {
        return new Queue("Pedidos_Criados_3");
    }

    @Bean
    public Queue pedidosExcluidos3Queue() {
        return new Queue("Pedidos_Excluidos_3");
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("ECommerceExchange");
    }

    @Bean
    public Binding bindPedido3CriadoQueue(Queue pedidosCriados3Queue, TopicExchange exchange) {
        return BindingBuilder.bind(pedidosCriados3Queue).to(exchange).with("pedidos.criados");
    }

    @Bean
    public Binding bindPedido3ExcluidoQueue(Queue pedidosExcluidos3Queue, TopicExchange exchange) {
        return BindingBuilder.bind(pedidosExcluidos3Queue).to(exchange).with("pedidos.excluidos");
    }
}
