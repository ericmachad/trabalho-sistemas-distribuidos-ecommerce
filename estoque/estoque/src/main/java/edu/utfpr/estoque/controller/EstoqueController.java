package edu.utfpr.estoque.controller;

import edu.utfpr.estoque.service.EstoqueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/estoque")
public class EstoqueController {

    private final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @GetMapping("/{produtoId}")
    public ResponseEntity<Integer> consultarEstoque(@PathVariable int produtoId) {
        int quantidade = estoqueService.consultarEstoque(produtoId);
        return ResponseEntity.ok(quantidade);
    }

}
