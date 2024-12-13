package edu.utfpr.ecommerce;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("ecommerce_exchange");
    }

    @Bean
    public Queue pedidosCriadosQueue() {
        return new Queue("pedidos_criados", true);
    }

    @Bean
    public Queue pagamentosAprovadosQueue() {
        return new Queue("pagamentos_aprovados", true);
    }

    @Bean
    public Queue pagamentosRejeitadosQueue() {
        return new Queue("pagamentos_rejeitados", true);
    }

    @Bean
    public Queue pedidosExcluidosQueue() {
        return new Queue("Pedidos_Excluidos");
    }

    @Bean
    public Queue pedidosEnviadosQueue() {
        return new Queue("Pedidos_Enviados");
    }

    @Bean
    public Binding bindPedidoCriado(Queue pedidoCriadoFila, TopicExchange exchange) {
        return BindingBuilder.bind(pedidoCriadoFila).to(exchange).with("pedidos.criados");
    }

    @Bean
    public Binding bindPagamentoAprovado(Queue pagamentosAprovadosQueue, TopicExchange exchange) {
        return BindingBuilder.bind(pagamentosAprovadosQueue).to(exchange).with("pagamentos.aprovados");
    }

    @Bean
    public Binding bindPagamentoRejeitado(Queue pagamentoRejeitadoFila, TopicExchange exchange) {
        return BindingBuilder.bind(pagamentoRejeitadoFila).to(exchange).with("pagamentos.rejeitados");
    }
}
