import { CommonModule } from '@angular/common';
import { NotificacaoService } from './../../services/notificacao.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-notificacao',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './notificacao.component.html',
  styleUrl: './notificacao.component.scss'
})
export class NotificacaoComponent implements OnInit{
  notificacoes: string[] = [];

  constructor(private notificacaoService: NotificacaoService) {}

  ngOnInit(): void {
    console.log("chego")
      this.notificacaoService.obterNotificacoes().subscribe({
        next: (data) => { 
          console.log('Notificacao recebida: ', data);
          this.notificacoes.push(data);
        },
        error: (error) => console.error('Erro ao receber notificacoes ', error)
      });
  }
}
