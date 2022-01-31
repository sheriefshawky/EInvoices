import { IDocument } from 'app/shared/model/document.model';

export interface ITaxTotal {
  id?: number;
  taxType?: string | null;
  amount?: number | null;
  document?: IDocument | null;
}

export const defaultValue: Readonly<ITaxTotal> = {};
