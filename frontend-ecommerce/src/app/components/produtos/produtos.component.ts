import { Produto } from './../../model/produto.model';
import { CommonModule } from '@angular/common';
import { ProdutoService } from './../../services/produtos.service';
import { Component, OnInit } from '@angular/core';
import { Pedido } from '../../model/pedido.model';
import { PagamentoService } from '../../services/pagamento.service';
import { Pagamento } from '../../model/pagamento.model';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { Notificacao } from '../../model/notificacao.model';
import { NotificacaoService } from '../../services/notificacao.service';

@Component({
  selector: 'app-produtos',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatIconModule, MatCardModule],
  templateUrl: './produtos.component.html',
  styleUrl: './produtos.component.scss'
})
export class ProdutosComponent implements OnInit {

  produtos: Produto[] = new Array<Produto>();
  pedido!: Pedido;
  pedidos: Pedido[] = new Array<Pedido>();
  notificacaoRecebida!: Notificacao;

  constructor(private produtoService: ProdutoService,
    private pagamentoService: PagamentoService,
    private notificacaoService: NotificacaoService
  ) {
    this.pedido = {
      id: 0,
      status: '',
      produtos: new Map<number, Produto>(),
      total: 0
    };
  }

  ngOnInit(): void {
    this.produtoService.listarProdutos().subscribe((data) => {
      this.produtos = data;
    });

    this.notificacaoService.notificacao$.subscribe({
      next: (notificacao) => {
        this.notificacaoRecebida = notificacao;
        console.log(this.notificacaoRecebida);
        console.log(this.notificacaoRecebida.mensagem);
        if(notificacao.tipo == 'pagamento'){
          let index = this.pedidos.findIndex((e) => e.id == notificacao.item.pedido.id);
          console.log(index);
          if(index >= 0){
            this.pedidos[index].status = notificacao.item.status
            this.pedidos = [...this.pedidos];
          }
        }
        else if(notificacao.tipo == 'envio'){
          let index = this.pedidos.findIndex((e) => e.id == notificacao.item.id);
          console.log(index);
          if(index >= 0){
            this.pedidos[index].status = notificacao.item.status
            this.pedidos = [...this.pedidos];
          }
        }
        else if(notificacao.tipo == 'pedido'){
          let index = this.pedidos.findIndex((e) => e.id == notificacao.item.id);
          console.log(index);
          if(index >= 0){
            this.pedidos[index].status = notificacao.item.status
            this.pedidos = [...this.pedidos];
          }
        }
      },
      error: (error) => console.error('Erro ao receber notificação', error),
    });
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

  limparCarrinho(): void {
    this.pedido.produtos.clear();
  }

  finalizarPedido(): void {
    let pedido = {
      produtos: Array.from(this.pedido.produtos.values()),
      total: this.pedido.total
    }
    this.produtoService.finalizarPedido(pedido).subscribe({
      next: (data) => {
        if (data.sucesso) {
          this.pedidos.push(data.pedido);
          this.pedidos = [...this.pedidos];
          this.limparCarrinho();
          alert(data.mensagem);
        }
      },
      error: (err) => console.error("Erro: ", err)
    })
  }

  realizarPagamento(pedido: Pedido): void {
    let pagamento: Pagamento = {
      idTransacao: Math.random() * 1000 + Math.random() * 1000,
      status: "Aprovado",
      pedido: pedido
    }
    this.pagamentoService.realizarPagamento(pagamento).subscribe(data => console.log(data))
  }

  recusarPagamento(pedido: Pedido): void {
    let pagamento: Pagamento = {
      idTransacao: Math.random() * 1000 + Math.random() * 1000,
      status: "Recusado",
      pedido: pedido
    }
    this.pagamentoService.recusarPagamento(pagamento).subscribe(data => console.log(data))
  }

  excluirPedido(pedido: Pedido): void {
    this.produtoService.excluirPedido(pedido.id).subscribe(data => console.log(data))
  }
}