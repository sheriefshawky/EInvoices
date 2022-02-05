import { IDocument } from 'app/shared/model/document.model';

export interface ISignature {
  id?: number;
  type?: string | null;
  sigValue?: string | null;
  document?: IDocument | null;
}

export const defaultValue: Readonly<ISignature> = {};
