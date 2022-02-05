import { IItemValue } from 'app/shared/model/item-value.model';
import { IDiscount } from 'app/shared/model/discount.model';
import { ITaxableItem } from 'app/shared/model/taxable-item.model';
import { IDocument } from 'app/shared/model/document.model';

export interface IInvoiceLine {
  id?: number;
  description?: string | null;
  itemType?: string | null;
  itemCode?: string | null;
  unitType?: string | null;
  quantity?: number | null;
  salesTotal?: number | null;
  total?: number | null;
  valueDifference?: number | null;
  totalTaxableFees?: number | null;
  netTotal?: number | null;
  itemsDiscount?: number | null;
  internalCode?: string | null;
  unitValue?: IItemValue | null;
  discount?: IDiscount | null;
  taxableItems?: ITaxableItem[] | null;
  document?: IDocument | null;
}

export const defaultValue: Readonly<IInvoiceLine> = {};
