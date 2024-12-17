package utfpr.edu.entrega.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utfpr.edu.entrega.model.Pedido;
import utfpr.edu.entrega.service.PagamentoService;

@RestController
@RequestMapping("/api/entrega")
public class EntregaController {
    private PagamentoService pagamentoService;

    public EntregaController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping
    public ResponseEntity<String> gerenciarEntrega(Pedido pedido) {
        pagamentoService.gerenciarEntrega(pedido);
        return ResponseEntity.ok("Pedido enviado");
    }
}
