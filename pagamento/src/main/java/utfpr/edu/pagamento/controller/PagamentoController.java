package utfpr.edu.pagamento.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utfpr.edu.pagamento.model.Pagamento;
import utfpr.edu.pagamento.service.PagamentoService;

@RestController
@RequestMapping("/api/pagamento")
public class PagamentoController {
    public PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping("/webhook")
    public ResponseEntity<Pagamento> processarPagamento(@RequestBody Pagamento pagamento){
        pagamentoService.processarPagamento(pagamento);
        return ResponseEntity.ok(pagamento);
    }
}
