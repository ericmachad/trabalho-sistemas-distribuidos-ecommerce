package edu.utfpr.ecommerce;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

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
    public Queue pedidosCriadosQueue() {
        return new Queue("Pedidos_Criados");
    }

    @Bean
    public Queue pedidosExcluidosQueue() {
        return new Queue("Pedidos_Excluidos");
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("ECommerceExchange");
    }

    @Bean
    public Binding bindPedidoCriado(Queue pedidosCriadosQueue, TopicExchange exchange) {
        return BindingBuilder.bind(pedidosCriadosQueue).to(exchange).with("pedidos.criados");
    }

    @Bean
    public Binding bindPedidoExcluido(Queue pedidosExcluidosQueue, TopicExchange exchange) {
        return BindingBuilder.bind(pedidosExcluidosQueue).to(exchange).with("pedidos.excluidos");
    }
}
