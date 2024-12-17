package edu.utfpr.ecommerce.controller;

import edu.utfpr.ecommerce.model.Produto;
import edu.utfpr.ecommerce.service.PedidoPublisher;
import edu.utfpr.ecommerce.model.Pedido;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<String> criarPedido(@RequestBody Pedido pedido) {
        if (pedido == null || pedido.getProdutos() == null || pedido.getProdutos().isEmpty()) {
            return ResponseEntity.badRequest().body("Pedido inválido! Adicione itens ao seu pedido");
        }
        pedido.setId(pedidos.size() + 1L);
        pedidos.add(pedido);
        pedidoPublisher.publicarPedidoCriado(pedido);
        return ResponseEntity.ok("Pedido " + pedido.getId() + " criado com sucesso!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirPedido(@PathVariable Long id) {
        Optional<Pedido> pedido = pedidos.stream().filter(p -> p.getId() == id).findFirst();
        if (pedido.isPresent()) {
            Pedido pedidoASerExcluido = pedido.get();
            pedidos.remove(pedidoASerExcluido);
            pedidoPublisher.publicarPedidoExcluido(pedidoASerExcluido);
            return ResponseEntity.ok("Pedido excluído com sucesso");
        }
        return ResponseEntity.notFound().build();
    }

}
