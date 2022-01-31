import { IIssuerAddress } from 'app/shared/model/issuer-address.model';

export interface IIssuer {
  id?: number;
  type?: string | null;
  name?: string | null;
  address?: IIssuerAddress | null;
}

export const defaultValue: Readonly<IIssuer> = {};
