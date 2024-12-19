import { CommonModule } from '@angular/common';
import { NotificacaoService } from './../../services/notificacao.service';
import { Component, OnInit } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatTable } from '@angular/material/table';

@Component({
  selector: 'app-notificacao',
  standalone: true,
  imports: [CommonModule, MatCardModule],
  templateUrl: './notificacao.component.html',
  styleUrl: './notificacao.component.scss'
})
export class NotificacaoComponent implements OnInit{
  notificacoes: string[] = [];
  displayedColumnsNotificacoes = ['mensagem'];
  constructor(private notificacaoService: NotificacaoService) {}

  ngOnInit(): void {
      this.notificacaoService.obterNotificacoes().subscribe({
        next: (data) => {
          this.notificacoes.push(data.mensagem);
          this.notificacaoService.emitirNotificacao(data);
        },
        error: (error) => console.error('Erro ao receber notificacoes ', error)
      });
  }
}
