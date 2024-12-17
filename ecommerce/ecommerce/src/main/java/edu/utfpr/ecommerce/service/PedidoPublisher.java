package edu.utfpr.ecommerce.service;

import edu.utfpr.ecommerce.model.Pedido;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PedidoPublisher {
    private final RabbitTemplate rabbitTemplate;
    @Value("${routing-key-pedidos-criados}")
    private String routingKeyPedidoCriado;
    @Value("${routing-key-pedidos-excluidos}")
    private String routingKeyPedidoExcluido;

    public PedidoPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publicarPedidoCriado(Pedido pedido) {
        rabbitTemplate.convertAndSend("ECommerceExchange", routingKeyPedidoCriado, pedido);
    }

    public void publicarPedidoExcluido(Pedido pedido) {
        rabbitTemplate.convertAndSend("ECommerceExchange", routingKeyPedidoExcluido, pedido);
    }
}
