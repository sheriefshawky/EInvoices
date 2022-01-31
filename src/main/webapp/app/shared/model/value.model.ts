export interface IValue {
  id?: number;
  currencySold?: string | null;
  amountEGP?: number | null;
  amountSold?: number | null;
  currencyExchangeRate?: number | null;
}

export const defaultValue: Readonly<IValue> = {};
