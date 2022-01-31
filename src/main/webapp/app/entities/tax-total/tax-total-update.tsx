import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IDocument } from 'app/shared/model/document.model';
import { getEntities as getDocuments } from 'app/entities/document/document.reducer';
import { getEntity, updateEntity, createEntity, reset } from './tax-total.reducer';
import { ITaxTotal } from 'app/shared/model/tax-total.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TaxTotalUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const documents = useAppSelector(state => state.document.entities);
  const taxTotalEntity = useAppSelector(state => state.taxTotal.entity);
  const loading = useAppSelector(state => state.taxTotal.loading);
  const updating = useAppSelector(state => state.taxTotal.updating);
  const updateSuccess = useAppSelector(state => state.taxTotal.updateSuccess);
  const handleClose = () => {
    props.history.push('/tax-total');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getDocuments({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...taxTotalEntity,
      ...values,
      document: documents.find(it => it.id.toString() === values.document.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...taxTotalEntity,
          document: taxTotalEntity?.document?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eInvoicesApp.taxTotal.home.createOrEditLabel" data-cy="TaxTotalCreateUpdateHeading">
            <Translate contentKey="eInvoicesApp.taxTotal.home.createOrEditLabel">Create or edit a TaxTotal</Translate>
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
                  id="tax-total-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eInvoicesApp.taxTotal.taxType')}
                id="tax-total-taxType"
                name="taxType"
                data-cy="taxType"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.taxTotal.amount')}
                id="tax-total-amount"
                name="amount"
                data-cy="amount"
                type="text"
              />
              <ValidatedField
                id="tax-total-document"
                name="document"
                data-cy="document"
                label={translate('eInvoicesApp.taxTotal.document')}
                type="select"
              >
                <option value="" key="0" />
                {documents
                  ? documents.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/tax-total" replace color="info">
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

export default TaxTotalUpdate;
