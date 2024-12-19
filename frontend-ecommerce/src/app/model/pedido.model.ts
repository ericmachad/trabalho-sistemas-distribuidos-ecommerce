import { Produto } from "./produto.model";

export class Pedido {
    id!: number;
    status!: string;
    produtos!: Map<number, Produto>;
    total!: number;
}