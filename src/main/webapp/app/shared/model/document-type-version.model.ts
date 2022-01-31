import dayjs from 'dayjs';
import { IWorkflowParameters } from 'app/shared/model/workflow-parameters.model';
import { IDocumentType } from 'app/shared/model/document-type.model';

export interface IDocumentTypeVersion {
  id?: number;
  name?: string | null;
  description?: string | null;
  versionNumber?: number | null;
  status?: string | null;
  activeFrom?: string | null;
  activeTo?: string | null;
  workflowParameters?: IWorkflowParameters[] | null;
  documentType?: IDocumentType | null;
}

export const defaultValue: Readonly<IDocumentTypeVersion> = {};
