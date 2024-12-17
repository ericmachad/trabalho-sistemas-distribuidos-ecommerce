package edu.utfpr.estoque.service;

import edu.utfpr.estoque.model.Pedido;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EstoqueService {
    private final Map<Integer, Integer> estoque = new HashMap<>();

    public EstoqueService() {
        estoque.put(1, 30);
        estoque.put(2, 30);
        estoque.put(3, 30);
    }
    public int consultarEstoque(int produtoId) {
        return estoque.getOrDefault(produtoId, 0);
    }

    public void atualizarEstoque(int produtoId, int quantidade) {
        estoque.put(produtoId, estoque.getOrDefault(produtoId, 0) + quantidade);
    }

    public void retirarEstoque(int produtoId, int quantidade) {
        estoque.put(produtoId, estoque.getOrDefault(produtoId, 0) - quantidade);
    }

    @RabbitListener(queues = "${queue.pedidos-criados}")
    public void processarPedidoCriado(Pedido pedido) {
        System.out.println("Chegou");
        if(pedido != null && pedido.getProdutos().size() > 0){
            pedido.getProdutos().forEach(e -> {
                retirarEstoque(e.getId(), e.getQuantidade());
            });
        }
    }

    @RabbitListener(queues = "${queue.pedidos-excluidos}")
    public void processarPedidoExcluido(Pedido pedido) {
        if(pedido != null && pedido.getProdutos().size() > 0){
            pedido.getProdutos().forEach(e -> {
                atualizarEstoque(e.getId(), e.getQuantidade());
            });
        }
    }
}
