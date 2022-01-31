import { IReceiverAddress } from 'app/shared/model/receiver-address.model';

export interface IReceiver {
  id?: number;
  type?: string | null;
  name?: string | null;
  address?: IReceiverAddress | null;
}

export const defaultValue: Readonly<IReceiver> = {};
