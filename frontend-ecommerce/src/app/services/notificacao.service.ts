import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Notificacao } from '../model/notificacao.model';

@Injectable({
  providedIn: 'root'
})
export class NotificacaoService {
  private sseUrl = 'http://localhost:8084/api/notificacao';
  private notificacaoSubject = new Subject<Notificacao>();
  notificacao$ = this.notificacaoSubject.asObservable();

  constructor() { }

  obterNotificacoes(): Observable<any> {
    return new Observable<any>((observer) => {
      const eventSource = new EventSource(this.sseUrl);

      eventSource.addEventListener('notificacao', (event: MessageEvent) => {
        observer.next(JSON.parse(event.data))
      });

      eventSource.onerror = (error) => {
        console.error('Erro no SSE: ' + error);
        eventSource.close();
        console.error((error));
      }

      return () => {
        eventSource.close();
      }
    });
  }

  emitirNotificacao(notificacao: Notificacao): void {
    this.notificacaoSubject.next(notificacao);
  }
}
