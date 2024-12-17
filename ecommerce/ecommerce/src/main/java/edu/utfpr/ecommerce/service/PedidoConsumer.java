package edu.utfpr.ecommerce.service;

import edu.utfpr.ecommerce.model.Pedido;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class PedidoConsumer {

    @RabbitListener(queues = "${queue.pagamentos-aprovados}")
    public void consumirPagamentoAprovado(Pedido pedido) {
        System.out.println("Pagamento aprovado para o pedido: " + pedido.getId());
    }

    @RabbitListener(queues = "${queue.pagamentos-recusados}")
    public void consumirPagamentoRecusado(Pedido pedido) {
        System.out.println("Pagamento recusado para o pedido: " + pedido.getId());
    }
}
