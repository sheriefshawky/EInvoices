export interface IPayment {
  id?: number;
  bankName?: string | null;
  bankAddress?: string | null;
  bankAccountNo?: string | null;
  bankAccountIBAN?: string | null;
  swiftCode?: string | null;
  terms?: string | null;
}

export const defaultValue: Readonly<IPayment> = {};
