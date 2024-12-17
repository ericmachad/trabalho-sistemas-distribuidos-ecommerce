package utfpr.edu.entrega.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import utfpr.edu.entrega.model.Pedido;

@Service
public class PagamentoService {
    private final RabbitTemplate rabbitTemplate;
    private final EntregaService entregaService;

    public PagamentoService(RabbitTemplate rabbitTemplate, EntregaService entregaService) {
        this.rabbitTemplate = rabbitTemplate;
        this.entregaService = entregaService;
    }

    @RabbitListener(queues = "${queue.pagamentos-aprovados}")
    public void gerenciarEntrega(Pedido pedido){
        System.out.println("Pedido "+ pedido.getId() + " enviado");
        entregaService.entregar(pedido);
    }

}
