import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Produto } from '../model/produto.model';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProdutosService {
  private urlProdutos = 'http://localhost:8080/api/pedido/produtos';
  constructor(private http: HttpClient) { }

  listarProdutos(): Observable<Produto[]> {
    return this.http.get<Produto[]>(this.urlProdutos);
  }
}
