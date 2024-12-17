package utfpr.edu.pagamento.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import utfpr.edu.pagamento.model.Pagamento;

@Service
public class PagamentoService {
    private final RabbitTemplate rabbitTemplate;

    public PagamentoService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void processarPagamento(Pagamento pagamento){
        if (pagamento.getStatus().equalsIgnoreCase("APROVADO")) {
            pagamentoAprovado(pagamento);
        } else {
            pagamentoRecusado(pagamento);
        }
    }

    public void pagamentoAprovado(Pagamento pagamento) {
        System.out.println("CHEGOU");
        rabbitTemplate.convertAndSend("ECommerceExchange", "pagamentos.aprovado", pagamento);
    }
    public void pagamentoRecusado(Pagamento pagamento) {
        rabbitTemplate.convertAndSend("ECommerceExchange", "pagamentos.recusado", pagamento);
    }

}
