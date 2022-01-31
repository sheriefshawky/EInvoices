import dayjs from 'dayjs';
import { IDocumentTypeVersion } from 'app/shared/model/document-type-version.model';

export interface IDocumentType {
  id?: number;
  name?: string | null;
  description?: string | null;
  activeFrom?: string | null;
  activeTo?: string | null;
  documentTypeVersions?: IDocumentTypeVersion[] | null;
}

export const defaultValue: Readonly<IDocumentType> = {};
