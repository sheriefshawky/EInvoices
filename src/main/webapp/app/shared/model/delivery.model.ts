export interface IDelivery {
  id?: number;
  approach?: string | null;
  packaging?: string | null;
  dateValidity?: string | null;
  exportPort?: string | null;
  countryOfOrigin?: string | null;
  grossWeight?: string | null;
  netWeight?: number | null;
  terms?: string | null;
}

export const defaultValue: Readonly<IDelivery> = {};
