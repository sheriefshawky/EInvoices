import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IInvoiceLine } from 'app/shared/model/invoice-line.model';
import { getEntities as getInvoiceLines } from 'app/entities/invoice-line/invoice-line.reducer';
import { getEntity, updateEntity, createEntity, reset } from './taxable-item.reducer';
import { ITaxableItem } from 'app/shared/model/taxable-item.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TaxableItemUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const invoiceLines = useAppSelector(state => state.invoiceLine.entities);
  const taxableItemEntity = useAppSelector(state => state.taxableItem.entity);
  const loading = useAppSelector(state => state.taxableItem.loading);
  const updating = useAppSelector(state => state.taxableItem.updating);
  const updateSuccess = useAppSelector(state => state.taxableItem.updateSuccess);
  const handleClose = () => {
    props.history.push('/taxable-item');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getInvoiceLines({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...taxableItemEntity,
      ...values,
      invoiceLine: invoiceLines.find(it => it.id.toString() === values.invoiceLine.toString()),
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
          ...taxableItemEntity,
          invoiceLine: taxableItemEntity?.invoiceLine?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eInvoicesApp.taxableItem.home.createOrEditLabel" data-cy="TaxableItemCreateUpdateHeading">
            <Translate contentKey="eInvoicesApp.taxableItem.home.createOrEditLabel">Create or edit a TaxableItem</Translate>
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
                  id="taxable-item-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eInvoicesApp.taxableItem.taxType')}
                id="taxable-item-taxType"
                name="taxType"
                data-cy="taxType"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.taxableItem.amount')}
                id="taxable-item-amount"
                name="amount"
                data-cy="amount"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.taxableItem.subType')}
                id="taxable-item-subType"
                name="subType"
                data-cy="subType"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.taxableItem.rate')}
                id="taxable-item-rate"
                name="rate"
                data-cy="rate"
                type="text"
              />
              <ValidatedField
                id="taxable-item-invoiceLine"
                name="invoiceLine"
                data-cy="invoiceLine"
                label={translate('eInvoicesApp.taxableItem.invoiceLine')}
                type="select"
              >
                <option value="" key="0" />
                {invoiceLines
                  ? invoiceLines.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/taxable-item" replace color="info">
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

export default TaxableItemUpdate;
