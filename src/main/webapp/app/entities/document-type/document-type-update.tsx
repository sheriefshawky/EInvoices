import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './document-type.reducer';
import { IDocumentType } from 'app/shared/model/document-type.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const DocumentTypeUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const documentTypeEntity = useAppSelector(state => state.documentType.entity);
  const loading = useAppSelector(state => state.documentType.loading);
  const updating = useAppSelector(state => state.documentType.updating);
  const updateSuccess = useAppSelector(state => state.documentType.updateSuccess);
  const handleClose = () => {
    props.history.push('/document-type');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
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
      ...documentTypeEntity,
      ...values,
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
          ...documentTypeEntity,
          activeFrom: convertDateTimeFromServer(documentTypeEntity.activeFrom),
          activeTo: convertDateTimeFromServer(documentTypeEntity.activeTo),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eInvoicesApp.documentType.home.createOrEditLabel" data-cy="DocumentTypeCreateUpdateHeading">
            <Translate contentKey="eInvoicesApp.documentType.home.createOrEditLabel">Create or edit a DocumentType</Translate>
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
                  id="document-type-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eInvoicesApp.documentType.name')}
                id="document-type-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.documentType.description')}
                id="document-type-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.documentType.activeFrom')}
                id="document-type-activeFrom"
                name="activeFrom"
                data-cy="activeFrom"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('eInvoicesApp.documentType.activeTo')}
                id="document-type-activeTo"
                name="activeTo"
                data-cy="activeTo"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/document-type" replace color="info">
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

export default DocumentTypeUpdate;
