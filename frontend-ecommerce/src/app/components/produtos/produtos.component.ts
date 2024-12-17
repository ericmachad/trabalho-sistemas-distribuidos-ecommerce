import { ProdutosService } from './../../services/produtos.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-produtos',
  standalone: true,
  imports: [],
  templateUrl: './produtos.component.html',
  styleUrl: './produtos.component.scss'
})
export class ProdutosComponent implements OnInit{
  
  constructor(private produtoService: ProdutosService){

  }

  ngOnInit(): void {
    this.produtoService.listarProdutos().subscribe(data => {
      console.log(data)
    },
    (error) => {
      console.error('Erro ao carregar produtos:', error);
    })
  }
}
