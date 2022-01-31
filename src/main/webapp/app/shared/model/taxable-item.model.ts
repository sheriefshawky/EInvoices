import { IInvoiceLine } from 'app/shared/model/invoice-line.model';

export interface ITaxableItem {
  id?: number;
  taxType?: string | null;
  amount?: number | null;
  subType?: string | null;
  rate?: number | null;
  invoiceLine?: IInvoiceLine | null;
}

export const defaultValue: Readonly<ITaxableItem> = {};
