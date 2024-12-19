package utfpr.edu.notificacao;

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
    public Queue pedidosCriados2Queue() {
        return new Queue("Pedidos_Criados_2");
    }

    @Bean
    public Queue pedidosExcluidos2Queue() {
        return new Queue("Pedidos_Excluidos_2");
    }

    @Bean
    public Queue pagamentosAprovados2Queue() {
        return new Queue("Pagamentos_Aprovados_2");
    }

    @Bean
    public Queue pagamentosRecusados2Queue() {
        return new Queue("Pagamentos_Recusados_2");
    }

    @Bean
    public Queue pedidosEnviados2Queue() {
        return new Queue("Pedidos_Enviados_2");
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("ECommerceExchange");
    }

    @Bean
    public Binding bindPedidosCriados2Queue(Queue pedidosCriados2Queue, TopicExchange exchange) {
        return BindingBuilder.bind(pedidosCriados2Queue).to(exchange).with("pedidos.criados");
    }

    @Bean
    public Binding bindPedidosExcluidos2Queue(Queue pedidosExcluidos2Queue, TopicExchange exchange) {
        return BindingBuilder.bind(pedidosExcluidos2Queue).to(exchange).with("pedidos.excluidos");
    }

    @Bean
    public Binding bindPagamentosAprovados2Queue(Queue pagamentosAprovados2Queue, TopicExchange exchange) {
        return BindingBuilder.bind(pagamentosAprovados2Queue).to(exchange).with("pagamentos.aprovados");
    }

    @Bean
    public Binding bindPagamentosRecusados2Queue(Queue pagamentosRecusados2Queue, TopicExchange exchange) {
        return BindingBuilder.bind(pagamentosRecusados2Queue).to(exchange).with("pagamentos.recusados");
    }

    @Bean
    public Binding bindPedidosEnviados2Queue(Queue pedidosEnviados2Queue, TopicExchange exchange) {
        return BindingBuilder.bind(pedidosEnviados2Queue).to(exchange).with("pedidos.enviados");
    }
}
