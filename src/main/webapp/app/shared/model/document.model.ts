import dayjs from 'dayjs';
import { IIssuer } from 'app/shared/model/issuer.model';
import { IReceiver } from 'app/shared/model/receiver.model';
import { IPayment } from 'app/shared/model/payment.model';
import { IDelivery } from 'app/shared/model/delivery.model';
import { IInvoiceLine } from 'app/shared/model/invoice-line.model';
import { ITaxTotal } from 'app/shared/model/tax-total.model';
import { ISignature } from 'app/shared/model/signature.model';

export interface IDocument {
  id?: number;
  documentType?: string | null;
  documentTypeVersion?: string | null;
  dateTimeIssued?: string | null;
  taxpayerActivityCode?: string | null;
  internalId?: string | null;
  purchaseOrderReference?: string | null;
  purchaseOrderDescription?: string | null;
  salesOrderReference?: string | null;
  salesOrderDescription?: string | null;
  proformaInvoiceNumber?: string | null;
  totalSalesAmount?: number | null;
  totalDiscountAmount?: number | null;
  netAmount?: number | null;
  extraDiscountAmount?: number | null;
  totalItemsDiscountAmount?: number | null;
  totalAmount?: number | null;
  issuer?: IIssuer | null;
  receiver?: IReceiver | null;
  payment?: IPayment | null;
  delivery?: IDelivery | null;
  invoiceLines?: IInvoiceLine[] | null;
  taxTotals?: ITaxTotal[] | null;
  signatures?: ISignature[] | null;
}

export const defaultValue: Readonly<IDocument> = {};
