package edu.utfpr.ecommerce.Service;

import edu.utfpr.ecommerce.model.Pedido;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoPublisher {
    private final RabbitTemplate rabbitTemplate;

    @Value(("${queue.pedidos-criados}"))
    private String pedidosCriadosQueue;
    @Value(("${queue.pedidos-excluidos}"))
    private String pedidosExcluidosQueue;

    public PedidoPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publicarPedidoCriado(Pedido pedido) {
        rabbitTemplate.convertAndSend(pedidosCriadosQueue, pedido);
    }

    public void publicarPedidoExcluido(Pedido pedido) {
        rabbitTemplate.convertAndSend(pedidosExcluidosQueue, pedido);
    }
}
