import { Produto } from './../../model/produto.model';
import { CommonModule } from '@angular/common';
import { ProdutoService } from './../../services/produtos.service';
import { Component, OnInit } from '@angular/core';
import { Pedido } from '../../model/pedido.model';
import { PagamentoService } from '../../services/pagamento.service';
import { Pagamento } from '../../model/pagamento.model';

@Component({
  selector: 'app-produtos',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './produtos.component.html',
  styleUrl: './produtos.component.scss'
})
export class ProdutosComponent implements OnInit {

  produtos: Produto[] = new Array<Produto>();
  pedido!: Pedido;

  constructor(private produtoService: ProdutoService,
    private pagamentoService: PagamentoService
  ) {
    this.pedido = {
      id: 0,
      produtos: new Map<number, Produto>(),
      total: 0
    }
  }

  ngOnInit(): void {
    this.produtoService.listarProdutos().subscribe((data) => {
      this.produtos = data;
    })
  }

  adicionarAoCarrinho(produto: Produto): void {
    const carrinho = this.pedido.produtos;
    if (carrinho.has(produto.id)) {
      const produtoExistente = carrinho.get(produto.id)!;
      produtoExistente.quantidade += 1;
    }
    else {
      produto.quantidade += 1;
      carrinho.set(produto.id, { ...produto });
    }

    this.atualizarTotal();
  }

  removerDoCarrinho(produto: Produto): void {
    const carrinho = this.pedido.produtos;
    if (carrinho.has(produto.id)) {
      carrinho.delete(produto.id)
    }
  }

  private atualizarTotal(): void {
    this.pedido.total = Array.from(this.pedido.produtos.values())
      .reduce((sum, produto) => sum + produto.preco * produto.quantidade, 0)
  }

  obterCarrinho(): Produto[] {
    return Array.from(this.pedido.produtos.values());
  }

  finalizarPedido(): void {
    let pedido = {
      id: 0,
      produtos: Array.from(this.pedido.produtos.values()),
      total: this.pedido.total
    }   
    this.produtoService.finalizarPedido(pedido).subscribe(data => console.log(data))
  }

  realizarPagamento(): void {
    let pagamento: Pagamento = {
      idTransacao: 1,
      status: "Aprovado"
    }
    this.pagamentoService.realizarPagamento(pagamento).subscribe(data => console.log(data))
  }
  recusarPagamento(): void {

  }
}
