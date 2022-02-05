import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IDocumentTypeVersion } from 'app/shared/model/document-type-version.model';
import { getEntities as getDocumentTypeVersions } from 'app/entities/document-type-version/document-type-version.reducer';
import { getEntity, updateEntity, createEntity, reset } from './workflow-parameters.reducer';
import { IWorkflowParameters } from 'app/shared/model/workflow-parameters.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const WorkflowParametersUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const documentTypeVersions = useAppSelector(state => state.documentTypeVersion.entities);
  const workflowParametersEntity = useAppSelector(state => state.workflowParameters.entity);
  const loading = useAppSelector(state => state.workflowParameters.loading);
  const updating = useAppSelector(state => state.workflowParameters.updating);
  const updateSuccess = useAppSelector(state => state.workflowParameters.updateSuccess);
  const handleClose = () => {
    props.history.push('/workflow-parameters');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getDocumentTypeVersions({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.activeFrom = convertDateTimeToServer(values.activeFrom);
    values.activeTo = convertDateTimeToServer(values.activeTo);

    const entity = {
      ...workflowParametersEntity,
      ...values,
      documentTypeVersion: documentTypeVersions.find(it => it.id.toString() === values.documentTypeVersion.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          activeFrom: displayDefaultDateTime(),
          activeTo: displayDefaultDateTime(),
        }
      : {
          ...workflowParametersEntity,
          activeFrom: convertDateTimeFromServer(workflowParametersEntity.activeFrom),
          activeTo: convertDateTimeFromServer(workflowParametersEntity.activeTo),
          documentTypeVersion: workflowParametersEntity?.documentTypeVersion?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eInvoicesApp.workflowParameters.home.createOrEditLabel" data-cy="WorkflowParametersCreateUpdateHeading">
            <Translate contentKey="eInvoicesApp.workflowParameters.home.createOrEditLabel">Create or edit a WorkflowParameters</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="workflow-parameters-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eInvoicesApp.workflowParameters.parameter')}
                id="workflow-parameters-parameter"
                name="parameter"
                data-cy="parameter"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.workflowParameters.wfValue')}
                id="workflow-parameters-wfValue"
                name="wfValue"
                data-cy="wfValue"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.workflowParameters.activeFrom')}
                id="workflow-parameters-activeFrom"
                name="activeFrom"
                data-cy="activeFrom"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('eInvoicesApp.workflowParameters.activeTo')}
                id="workflow-parameters-activeTo"
                name="activeTo"
                data-cy="activeTo"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="workflow-parameters-documentTypeVersion"
                name="documentTypeVersion"
                data-cy="documentTypeVersion"
                label={translate('eInvoicesApp.workflowParameters.documentTypeVersion')}
                type="select"
              >
                <option value="" key="0" />
                {documentTypeVersions
                  ? documentTypeVersions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/workflow-parameters" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default WorkflowParametersUpdate;
