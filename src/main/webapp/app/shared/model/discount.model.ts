export interface IDiscount {
  id?: number;
  rate?: number | null;
  amount?: number | null;
}

export const defaultValue: Readonly<IDiscount> = {};
