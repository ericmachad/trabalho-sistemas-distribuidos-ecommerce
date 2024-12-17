package utfpr.edu.entrega.service;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import utfpr.edu.entrega.model.Pedido;

public class EntregaService {
    RabbitTemplate rabbitTemplate;

    public EntregaService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener("${queue.pagamentos-aprovados}")
    @RabbitHandler("${queue.pagamentos-aprovados}")
    public void gerenciarEntrega(Pedido pedido){
        System.out.println("Pedido "+ pedido.getId() + " enviado");

    }

}
