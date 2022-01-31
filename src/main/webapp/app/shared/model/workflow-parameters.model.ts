import dayjs from 'dayjs';
import { IDocumentTypeVersion } from 'app/shared/model/document-type-version.model';

export interface IWorkflowParameters {
  id?: number;
  parameter?: string | null;
  value?: number | null;
  activeFrom?: string | null;
  activeTo?: string | null;
  documentTypeVersion?: IDocumentTypeVersion | null;
}

export const defaultValue: Readonly<IWorkflowParameters> = {};
