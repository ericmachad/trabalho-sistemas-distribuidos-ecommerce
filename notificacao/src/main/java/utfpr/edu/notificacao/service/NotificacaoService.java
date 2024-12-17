package utfpr.edu.notificacao.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import utfpr.edu.notificacao.model.Pagamento;
import utfpr.edu.notificacao.model.Pedido;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class NotificacaoService {
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.add(emitter);

        emitter.onCompletion(()->emitters.remove(emitter));
        emitter.onTimeout(()->emitters.remove(emitter));


        return emitter;
    }

    public void enviarNotificacao(String mensagem) {
        System.out.println("chegou");
        System.out.println(emitters.size());
        emitters.forEach(emitter -> {
            try{
                emitter.send(SseEmitter.event()
                        .name("notificacao")
                        .data(mensagem));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @RabbitListener(queues = "${queue.pedidos-criados}")
    public void gerenciarPedidosCriados(Pedido pedido) {
        System.out.println("Chegou" + pedido.getId());
        enviarNotificacao("Pedido Criado: " + pedido.getId());
    }

    @RabbitListener(queues = "${queue.pagamentos-aprovados}")
    public void gerenciarPagamentosAprovados(Pagamento pagamento) {
        enviarNotificacao("Pagamento Aprovado: " + pagamento.getIdTransacao());
    }

    @RabbitListener(queues = "${queue.pagamentos-recusados}")
    public void gerenciarPagamentosRecusados(Pagamento pagamento) {
        enviarNotificacao("Pagamento Recusado: " + pagamento.getIdTransacao());
    }

    @RabbitListener(queues = "${queue.pedidos-enviados}")
    public void gerenciarPedidosEnviados(Pedido pedido) {
        enviarNotificacao("Pedido Enviado: " + pedido.getId());
    }
}
