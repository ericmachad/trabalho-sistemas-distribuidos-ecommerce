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
        System.out.println(pagamento.getStatus());
        if (pagamento.getStatus().equalsIgnoreCase("APROVADO")) {
            pagamento.setStatus("Pagamento Aprovado");
            pagamentoAprovado(pagamento);
        } else {
            pagamento.setStatus("Pagamento Recusado");
            pagamentoRecusado(pagamento);
        }
    }

    public void pagamentoAprovado(Pagamento pagamento) {
        rabbitTemplate.convertAndSend("ECommerceExchange", "pagamentos.aprovados", pagamento);
    }
    public void pagamentoRecusado(Pagamento pagamento) {
        rabbitTemplate.convertAndSend("ECommerceExchange", "pagamentos.recusados", pagamento);
    }

}
