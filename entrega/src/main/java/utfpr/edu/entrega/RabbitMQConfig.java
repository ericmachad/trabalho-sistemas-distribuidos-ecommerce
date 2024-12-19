package utfpr.edu.entrega;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public Queue pedidosEnviadosQueue() {
        return new Queue("Pedidos_Enviados");
    }

    @Bean
    public Queue pagamentosAprovados4Queue() {
        return new Queue("Pagamentos_Aprovados_4");
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("ECommerceExchange");
    }

    @Bean
    public Binding bindPedidosEnviadosQueue(Queue pedidosEnviadosQueue, TopicExchange exchange) {
        return BindingBuilder.bind(pedidosEnviadosQueue).to(exchange).with("pedidos.enviados");
    }

    @Bean
    public Binding bindPagamentosAprovados4Queue(Queue pagamentosAprovados4Queue, TopicExchange exchange) {
        return BindingBuilder.bind(pagamentosAprovados4Queue).to(exchange).with("pagamentos.aprovados");
    }
}
