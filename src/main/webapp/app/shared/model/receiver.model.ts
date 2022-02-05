import { IReceiverAddress } from 'app/shared/model/receiver-address.model';

export interface IReceiver {
  id?: number;
  recieverType?: string | null;
  name?: string | null;
  address?: IReceiverAddress | null;
}

export const defaultValue: Readonly<IReceiver> = {};
