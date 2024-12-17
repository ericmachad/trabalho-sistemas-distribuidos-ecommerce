package utfpr.edu.entrega.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import utfpr.edu.entrega.model.Pedido;

@Service
public class EntregaService {
    private final RabbitTemplate rabbitTemplate;

    public EntregaService(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public void entregar(Pedido pedido){
        rabbitTemplate.convertAndSend("ECommerceExchange", "pedidos.enviados", pedido);
    }
}
