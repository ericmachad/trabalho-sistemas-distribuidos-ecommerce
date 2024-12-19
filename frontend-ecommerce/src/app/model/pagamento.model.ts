import { Pedido } from "./pedido.model";

export interface Pagamento {
    idTransacao: number;
    pedido: Pedido;
    status: string;
}