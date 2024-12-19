import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Produto } from '../model/produto.model';
import { HttpClient } from '@angular/common/http';
import { Pedido } from '../model/pedido.model';
import { Pagamento } from '../model/pagamento.model';

@Injectable({
  providedIn: 'root'
})
export class PagamentoService {
  private urlBase = 'http://localhost:8082/api/pagamento';
  constructor(private http: HttpClient) { }

  realizarPagamento(pagamento: Pagamento): Observable<string> {
    return this.http.post<string>(`${this.urlBase}/webhook`, pagamento)
  }
  
  recusarPagamento(pedido: any): Observable<string> {
    return this.http.post<string>(`${this.urlBase}/webhook`, pedido)
  }
}
