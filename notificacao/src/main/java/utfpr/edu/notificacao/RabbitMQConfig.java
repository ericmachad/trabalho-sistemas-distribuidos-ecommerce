package utfpr.edu.pagamento;

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
    public Queue pagamentosAprovadosQueue() {
        return new Queue("Pagamentos_Aprovados");
    }

    @Bean
    public Queue pagamentosRecusadosQueue() {
        return new Queue("Pagamentos_Recusados");
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("ECommerceExchange");
    }

    @Bean
    public Binding bindPagamentoAprovado(Queue pagamentosAprovadosQueue, TopicExchange exchange) {
        return BindingBuilder.bind(pagamentosAprovadosQueue).to(exchange).with("pagamentos.aprovados");
    }

    @Bean
    public Binding bindPagamentoRecusado(Queue pagamentosRecusadosQueue, TopicExchange exchange) {
        return BindingBuilder.bind(pagamentosRecusadosQueue).to(exchange).with("pagamentos.recusados");
    }
}
