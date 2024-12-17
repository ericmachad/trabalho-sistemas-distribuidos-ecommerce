import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NotificacaoComponent } from "./components/notificacao/notificacao.component";
import { ProdutosComponent } from "./components/produtos/produtos.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NotificacaoComponent, ProdutosComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'frontend-ecommerce';
}
