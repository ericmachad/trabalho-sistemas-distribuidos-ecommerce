import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NotificacaoService {
  private sseUrl = 'http://localhost:8084/api/notificacao'
  constructor() { }

  obterNotificacoes(): Observable<string> {
    return new Observable<string>((observer) => {
      const eventSource = new EventSource(this.sseUrl);

      eventSource.addEventListener('notificacao', (event: MessageEvent) => {
        observer.next(event.data)
      });

      eventSource.onerror =(error) => {
        console.error('Erro no SSE: ' + error);
        eventSource.close();
        console.error((error));
      } 

      return () => {
        eventSource.close();
      }
    });
  }
}
