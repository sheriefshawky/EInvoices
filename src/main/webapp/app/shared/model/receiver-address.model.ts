export interface IReceiverAddress {
  id?: number;
  country?: string | null;
  governate?: string | null;
  regionCity?: string | null;
  street?: string | null;
  buildingNumber?: string | null;
  postalCode?: string | null;
  floor?: string | null;
  room?: string | null;
  landmark?: string | null;
  additionalInformation?: string | null;
}

export const defaultValue: Readonly<IReceiverAddress> = {};
