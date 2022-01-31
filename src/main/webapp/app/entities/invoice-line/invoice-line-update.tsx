import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IValue } from 'app/shared/model/value.model';
import { getEntities as getValues } from 'app/entities/value/value.reducer';
import { IDiscount } from 'app/shared/model/discount.model';
import { getEntities as getDiscounts } from 'app/entities/discount/discount.reducer';
import { IDocument } from 'app/shared/model/document.model';
import { getEntities as getDocuments } from 'app/entities/document/document.reducer';
import { getEntity, updateEntity, createEntity, reset } from './invoice-line.reducer';
import { IInvoiceLine } from 'app/shared/model/invoice-line.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const InvoiceLineUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const values = useAppSelector(state => state.value.entities);
  const discounts = useAppSelector(state => state.discount.entities);
  const documents = useAppSelector(state => state.document.entities);
  const invoiceLineEntity = useAppSelector(state => state.invoiceLine.entity);
  const loading = useAppSelector(state => state.invoiceLine.loading);
  const updating = useAppSelector(state => state.invoiceLine.updating);
  const updateSuccess = useAppSelector(state => state.invoiceLine.updateSuccess);
  const handleClose = () => {
    props.history.push('/invoice-line');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getValues({}));
    dispatch(getDiscounts({}));
    dispatch(getDocuments({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...invoiceLineEntity,
      ...values,
      unitValue: values.find(it => it.id.toString() === values.unitValue.toString()),
      discount: discounts.find(it => it.id.toString() === values.discount.toString()),
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
          ...invoiceLineEntity,
          unitValue: invoiceLineEntity?.unitValue?.id,
          discount: invoiceLineEntity?.discount?.id,
          document: invoiceLineEntity?.document?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eInvoicesApp.invoiceLine.home.createOrEditLabel" data-cy="InvoiceLineCreateUpdateHeading">
            <Translate contentKey="eInvoicesApp.invoiceLine.home.createOrEditLabel">Create or edit a InvoiceLine</Translate>
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
                  id="invoice-line-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eInvoicesApp.invoiceLine.description')}
                id="invoice-line-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.invoiceLine.itemType')}
                id="invoice-line-itemType"
                name="itemType"
                data-cy="itemType"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.invoiceLine.itemCode')}
                id="invoice-line-itemCode"
                name="itemCode"
                data-cy="itemCode"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.invoiceLine.unitType')}
                id="invoice-line-unitType"
                name="unitType"
                data-cy="unitType"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.invoiceLine.quantity')}
                id="invoice-line-quantity"
                name="quantity"
                data-cy="quantity"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.invoiceLine.salesTotal')}
                id="invoice-line-salesTotal"
                name="salesTotal"
                data-cy="salesTotal"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.invoiceLine.total')}
                id="invoice-line-total"
                name="total"
                data-cy="total"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.invoiceLine.valueDifference')}
                id="invoice-line-valueDifference"
                name="valueDifference"
                data-cy="valueDifference"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.invoiceLine.totalTaxableFees')}
                id="invoice-line-totalTaxableFees"
                name="totalTaxableFees"
                data-cy="totalTaxableFees"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.invoiceLine.netTotal')}
                id="invoice-line-netTotal"
                name="netTotal"
                data-cy="netTotal"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.invoiceLine.itemsDiscount')}
                id="invoice-line-itemsDiscount"
                name="itemsDiscount"
                data-cy="itemsDiscount"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.invoiceLine.internalCode')}
                id="invoice-line-internalCode"
                name="internalCode"
                data-cy="internalCode"
                type="text"
              />
              <ValidatedField
                id="invoice-line-unitValue"
                name="unitValue"
                data-cy="unitValue"
                label={translate('eInvoicesApp.invoiceLine.unitValue')}
                type="select"
              >
                <option value="" key="0" />
                {values
                  ? values.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="invoice-line-discount"
                name="discount"
                data-cy="discount"
                label={translate('eInvoicesApp.invoiceLine.discount')}
                type="select"
              >
                <option value="" key="0" />
                {discounts
                  ? discounts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="invoice-line-document"
                name="document"
                data-cy="document"
                label={translate('eInvoicesApp.invoiceLine.document')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/invoice-line" replace color="info">
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

export default InvoiceLineUpdate;
