import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IDocumentType } from 'app/shared/model/document-type.model';
import { getEntities as getDocumentTypes } from 'app/entities/document-type/document-type.reducer';
import { getEntity, updateEntity, createEntity, reset } from './document-type-version.reducer';
import { IDocumentTypeVersion } from 'app/shared/model/document-type-version.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const DocumentTypeVersionUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const documentTypes = useAppSelector(state => state.documentType.entities);
  const documentTypeVersionEntity = useAppSelector(state => state.documentTypeVersion.entity);
  const loading = useAppSelector(state => state.documentTypeVersion.loading);
  const updating = useAppSelector(state => state.documentTypeVersion.updating);
  const updateSuccess = useAppSelector(state => state.documentTypeVersion.updateSuccess);
  const handleClose = () => {
    props.history.push('/document-type-version');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getDocumentTypes({}));
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
      ...documentTypeVersionEntity,
      ...values,
      documentType: documentTypes.find(it => it.id.toString() === values.documentType.toString()),
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
          ...documentTypeVersionEntity,
          activeFrom: convertDateTimeFromServer(documentTypeVersionEntity.activeFrom),
          activeTo: convertDateTimeFromServer(documentTypeVersionEntity.activeTo),
          documentType: documentTypeVersionEntity?.documentType?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eInvoicesApp.documentTypeVersion.home.createOrEditLabel" data-cy="DocumentTypeVersionCreateUpdateHeading">
            <Translate contentKey="eInvoicesApp.documentTypeVersion.home.createOrEditLabel">Create or edit a DocumentTypeVersion</Translate>
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
                  id="document-type-version-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eInvoicesApp.documentTypeVersion.name')}
                id="document-type-version-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.documentTypeVersion.description')}
                id="document-type-version-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.documentTypeVersion.versionNumber')}
                id="document-type-version-versionNumber"
                name="versionNumber"
                data-cy="versionNumber"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.documentTypeVersion.status')}
                id="document-type-version-status"
                name="status"
                data-cy="status"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.documentTypeVersion.activeFrom')}
                id="document-type-version-activeFrom"
                name="activeFrom"
                data-cy="activeFrom"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('eInvoicesApp.documentTypeVersion.activeTo')}
                id="document-type-version-activeTo"
                name="activeTo"
                data-cy="activeTo"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="document-type-version-documentType"
                name="documentType"
                data-cy="documentType"
                label={translate('eInvoicesApp.documentTypeVersion.documentType')}
                type="select"
              >
                <option value="" key="0" />
                {documentTypes
                  ? documentTypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/document-type-version" replace color="info">
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

export default DocumentTypeVersionUpdate;
