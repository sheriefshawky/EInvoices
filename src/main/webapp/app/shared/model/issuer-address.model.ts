export interface IIssuerAddress {
  id?: number;
  branchId?: string | null;
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

export const defaultValue: Readonly<IIssuerAddress> = {};
