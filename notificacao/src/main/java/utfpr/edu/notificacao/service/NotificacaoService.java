package utfpr.edu.notificacao.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import utfpr.edu.notificacao.model.Pagamento;
import utfpr.edu.notificacao.model.Pedido;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class NotificacaoService {
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));


        return emitter;
    }

    public void enviarNotificacao(Map<String, Object> mensagem) {
        System.out.println("chegou");
        System.out.println(emitters.size());
        emitters.forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event()
                        .name("notificacao")
                        .data(mensagem));
            } catch (IOException e) {
                System.out.println("Erro ao enviar SSE: " + e.getMessage());
                emitters.remove(emitter); // Remove o emitter desconectado
            }
        });
    }

    @RabbitListener(queues = "${queue.pedidos-criados}")
    public void gerenciarPedidosCriados(Pedido pedido) {
        System.out.println("Chegou" + pedido.getId());
        enviarNotificacao(respose("PEDIDO " + pedido.getId() + " CRIADO", pedido, "pedido"));
    }

    @RabbitListener(queues = "${queue.pedidos-excluidos}")
    public void gerenciarPedidosExcluidos(Pedido pedido) {
        System.out.println("Chegou" + pedido.getId());
        enviarNotificacao(respose("PEDIDO " + pedido.getId() + " EXCLUIDO", pedido, "pedido"));
    }

    @RabbitListener(queues = "${queue.pagamentos-aprovados}")
    public void gerenciarPagamentosAprovados(Pagamento pagamento) {
        enviarNotificacao(respose("PEDIDO" + pagamento.getPedido().getId() + ": pagamento APROVADO. Transação: " + pagamento.getIdTransacao(), pagamento, "pagamento"));
    }

    @RabbitListener(queues = "${queue.pagamentos-recusados}")
    public void gerenciarPagamentosRecusados(Pagamento pagamento) {
        enviarNotificacao(respose("PEDIDO" + pagamento.getPedido().getId() + ": pagamento RECUSADO. Transação: " + pagamento.getIdTransacao(), pagamento, "pagamento"));
    }

    @RabbitListener(queues = "${queue.pedidos-enviados}")
    public void gerenciarPedidosEnviados(Pedido pedido) {
        enviarNotificacao(respose("PEDIDO " + pedido.getId() + " ENVIADO", pedido, "envio"));
    }

    private HashMap<String, Object> respose(String mensagem, Object objeto, String tipo) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("mensagem", mensagem);
        response.put("item", objeto);
        response.put("tipo", tipo);
        return response;
    }
}
