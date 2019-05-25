export const enum Acepta {
  NO = 'NO',
  SI = 'SI'
}

export interface IBanco {
  id?: number;
  nombreDeFantasia?: string;
  numero?: number;
  razonSocial?: string;
  cuit?: string;
  otrosDatos?: string;
  activo?: boolean;
  diferidos?: Acepta;
}

export class Banco implements IBanco {
  constructor(
    public id?: number,
    public nombreDeFantasia?: string,
    public numero?: number,
    public razonSocial?: string,
    public cuit?: string,
    public otrosDatos?: string,
    public activo?: boolean,
    public diferidos?: Acepta
  ) {
    this.activo = this.activo || false;
  }
}
