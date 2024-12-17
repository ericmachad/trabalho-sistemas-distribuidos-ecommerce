import { Produto } from "./produto.model";

export interface Pedido {
    id: number;
    produtos: Map<number, Produto>;
    total: number;
}