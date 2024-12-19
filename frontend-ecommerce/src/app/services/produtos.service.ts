import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Produto } from '../model/produto.model';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProdutoService {
  private urlBase = 'http://localhost:8080/api/pedido';
  constructor(private http: HttpClient) { }

  listarProdutos(): Observable<Produto[]> {
    return this.http.get<Produto[]>(`${this.urlBase}/produtos`);
  }

  finalizarPedido(pedido: any): Observable<any> {
    return this.http.post<string>(`${this.urlBase}`, pedido)
  }

  excluirPedido(pedidoId: any): Observable<any> {
    return this.http.delete<string>(`${this.urlBase}/${pedidoId}`)
  }
}
