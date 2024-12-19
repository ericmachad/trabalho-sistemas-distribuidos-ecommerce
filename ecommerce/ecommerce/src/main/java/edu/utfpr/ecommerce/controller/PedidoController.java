package edu.utfpr.ecommerce.controller;

import edu.utfpr.ecommerce.model.Produto;
import edu.utfpr.ecommerce.service.PedidoPublisher;
import edu.utfpr.ecommerce.model.Pedido;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {

    private final PedidoPublisher pedidoPublisher;
    private final List<Pedido> pedidos = new ArrayList<>();
    private final List<Produto> produtos = new ArrayList<>();

    public PedidoController(PedidoPublisher pedidoPublisher) {
        this.pedidoPublisher = pedidoPublisher;
        produtos.add(new Produto(1L, "Produto 1", 0, 100.0));
        produtos.add(new Produto(2L, "Produto 2", 0, 100.0));
        produtos.add(new Produto(3L, "Produto 3", 0, 100.0));
    }

    @GetMapping("/produtos")
    public ResponseEntity<List<Produto>> listarProdutos() {
        return ResponseEntity.ok(produtos);
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listarPedidos() {
        if (pedidos.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("{id}")
    public ResponseEntity<Pedido> obterPedido(@PathVariable Long id) {
        if (id == null) return ResponseEntity.badRequest().build();
        Optional<Pedido> pedido = pedidos.stream().filter(p -> p.getId() == id).findFirst();
        if (pedido.isPresent()) return ResponseEntity.ok(pedido.get());
        return ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> criarPedido(@RequestBody Pedido pedido) {
        Map<String, Object> response = new HashMap<>();
        if (pedido == null || pedido.getProdutos() == null || pedido.getProdutos().isEmpty()) {
            response.put("mensagem", "Pedido inválido! Adicione itens ao seu pedido");
            response.put("sucesso", false);
            return ResponseEntity.badRequest().body(response);
        }
        pedido.setId(pedidos.size() + 1L);
        pedido.setStatus("Aguardando Pagamento");
        pedidos.add(pedido);
        pedidoPublisher.publicarPedidoCriado(pedido);
        response.put("mensagem", "Pedido " + pedido.getId() + " criado com sucesso com sucesso!");
        response.put("pedido", pedido);
        response.put("sucesso", true);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluirPedido(@PathVariable Long id) {
        Optional<Pedido> pedido = pedidos.stream().filter(p -> p.getId() == id).findFirst();
        if (pedido.isPresent()) {
            Pedido pedidoASerExcluido = pedido.get();
            pedidoASerExcluido.setStatus("Pedido Excluido");
            pedidos.remove(pedidoASerExcluido);
            pedidoPublisher.publicarPedidoExcluido(pedidoASerExcluido);
            Map<String, String> response = new HashMap<>();
            response.put("mensagem", "Pedido excluido com sucesso!");
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

}
