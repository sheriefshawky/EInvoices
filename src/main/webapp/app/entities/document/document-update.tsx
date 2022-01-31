import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IIssuer } from 'app/shared/model/issuer.model';
import { getEntities as getIssuers } from 'app/entities/issuer/issuer.reducer';
import { IReceiver } from 'app/shared/model/receiver.model';
import { getEntities as getReceivers } from 'app/entities/receiver/receiver.reducer';
import { IPayment } from 'app/shared/model/payment.model';
import { getEntities as getPayments } from 'app/entities/payment/payment.reducer';
import { IDelivery } from 'app/shared/model/delivery.model';
import { getEntities as getDeliveries } from 'app/entities/delivery/delivery.reducer';
import { getEntity, updateEntity, createEntity, reset } from './document.reducer';
import { IDocument } from 'app/shared/model/document.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const DocumentUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const issuers = useAppSelector(state => state.issuer.entities);
  const receivers = useAppSelector(state => state.receiver.entities);
  const payments = useAppSelector(state => state.payment.entities);
  const deliveries = useAppSelector(state => state.delivery.entities);
  const documentEntity = useAppSelector(state => state.document.entity);
  const loading = useAppSelector(state => state.document.loading);
  const updating = useAppSelector(state => state.document.updating);
  const updateSuccess = useAppSelector(state => state.document.updateSuccess);
  const handleClose = () => {
    props.history.push('/document');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getIssuers({}));
    dispatch(getReceivers({}));
    dispatch(getPayments({}));
    dispatch(getDeliveries({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.dateTimeIssued = convertDateTimeToServer(values.dateTimeIssued);

    const entity = {
      ...documentEntity,
      ...values,
      issuer: issuers.find(it => it.id.toString() === values.issuer.toString()),
      receiver: receivers.find(it => it.id.toString() === values.receiver.toString()),
      payment: payments.find(it => it.id.toString() === values.payment.toString()),
      delivery: deliveries.find(it => it.id.toString() === values.delivery.toString()),
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
          dateTimeIssued: displayDefaultDateTime(),
        }
      : {
          ...documentEntity,
          dateTimeIssued: convertDateTimeFromServer(documentEntity.dateTimeIssued),
          issuer: documentEntity?.issuer?.id,
          receiver: documentEntity?.receiver?.id,
          payment: documentEntity?.payment?.id,
          delivery: documentEntity?.delivery?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eInvoicesApp.document.home.createOrEditLabel" data-cy="DocumentCreateUpdateHeading">
            <Translate contentKey="eInvoicesApp.document.home.createOrEditLabel">Create or edit a Document</Translate>
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
                  id="document-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eInvoicesApp.document.documentType')}
                id="document-documentType"
                name="documentType"
                data-cy="documentType"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.document.documentTypeVersion')}
                id="document-documentTypeVersion"
                name="documentTypeVersion"
                data-cy="documentTypeVersion"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.document.dateTimeIssued')}
                id="document-dateTimeIssued"
                name="dateTimeIssued"
                data-cy="dateTimeIssued"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('eInvoicesApp.document.taxpayerActivityCode')}
                id="document-taxpayerActivityCode"
                name="taxpayerActivityCode"
                data-cy="taxpayerActivityCode"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.document.internalId')}
                id="document-internalId"
                name="internalId"
                data-cy="internalId"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.document.purchaseOrderReference')}
                id="document-purchaseOrderReference"
                name="purchaseOrderReference"
                data-cy="purchaseOrderReference"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.document.purchaseOrderDescription')}
                id="document-purchaseOrderDescription"
                name="purchaseOrderDescription"
                data-cy="purchaseOrderDescription"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.document.salesOrderReference')}
                id="document-salesOrderReference"
                name="salesOrderReference"
                data-cy="salesOrderReference"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.document.salesOrderDescription')}
                id="document-salesOrderDescription"
                name="salesOrderDescription"
                data-cy="salesOrderDescription"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.document.proformaInvoiceNumber')}
                id="document-proformaInvoiceNumber"
                name="proformaInvoiceNumber"
                data-cy="proformaInvoiceNumber"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.document.totalSalesAmount')}
                id="document-totalSalesAmount"
                name="totalSalesAmount"
                data-cy="totalSalesAmount"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.document.totalDiscountAmount')}
                id="document-totalDiscountAmount"
                name="totalDiscountAmount"
                data-cy="totalDiscountAmount"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.document.netAmount')}
                id="document-netAmount"
                name="netAmount"
                data-cy="netAmount"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.document.extraDiscountAmount')}
                id="document-extraDiscountAmount"
                name="extraDiscountAmount"
                data-cy="extraDiscountAmount"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.document.totalItemsDiscountAmount')}
                id="document-totalItemsDiscountAmount"
                name="totalItemsDiscountAmount"
                data-cy="totalItemsDiscountAmount"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.document.totalAmount')}
                id="document-totalAmount"
                name="totalAmount"
                data-cy="totalAmount"
                type="text"
              />
              <ValidatedField
                id="document-issuer"
                name="issuer"
                data-cy="issuer"
                label={translate('eInvoicesApp.document.issuer')}
                type="select"
              >
                <option value="" key="0" />
                {issuers
                  ? issuers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="document-receiver"
                name="receiver"
                data-cy="receiver"
                label={translate('eInvoicesApp.document.receiver')}
                type="select"
              >
                <option value="" key="0" />
                {receivers
                  ? receivers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="document-payment"
                name="payment"
                data-cy="payment"
                label={translate('eInvoicesApp.document.payment')}
                type="select"
              >
                <option value="" key="0" />
                {payments
                  ? payments.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="document-delivery"
                name="delivery"
                data-cy="delivery"
                label={translate('eInvoicesApp.document.delivery')}
                type="select"
              >
                <option value="" key="0" />
                {deliveries
                  ? deliveries.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/document" replace color="info">
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

export default DocumentUpdate;
