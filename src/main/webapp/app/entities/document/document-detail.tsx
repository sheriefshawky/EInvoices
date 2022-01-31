import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './document.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const DocumentDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const documentEntity = useAppSelector(state => state.document.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="documentDetailsHeading">
          <Translate contentKey="eInvoicesApp.document.detail.title">Document</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{documentEntity.id}</dd>
          <dt>
            <span id="documentType">
              <Translate contentKey="eInvoicesApp.document.documentType">Document Type</Translate>
            </span>
          </dt>
          <dd>{documentEntity.documentType}</dd>
          <dt>
            <span id="documentTypeVersion">
              <Translate contentKey="eInvoicesApp.document.documentTypeVersion">Document Type Version</Translate>
            </span>
          </dt>
          <dd>{documentEntity.documentTypeVersion}</dd>
          <dt>
            <span id="dateTimeIssued">
              <Translate contentKey="eInvoicesApp.document.dateTimeIssued">Date Time Issued</Translate>
            </span>
          </dt>
          <dd>
            {documentEntity.dateTimeIssued ? (
              <TextFormat value={documentEntity.dateTimeIssued} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="taxpayerActivityCode">
              <Translate contentKey="eInvoicesApp.document.taxpayerActivityCode">Taxpayer Activity Code</Translate>
            </span>
          </dt>
          <dd>{documentEntity.taxpayerActivityCode}</dd>
          <dt>
            <span id="internalId">
              <Translate contentKey="eInvoicesApp.document.internalId">Internal Id</Translate>
            </span>
          </dt>
          <dd>{documentEntity.internalId}</dd>
          <dt>
            <span id="purchaseOrderReference">
              <Translate contentKey="eInvoicesApp.document.purchaseOrderReference">Purchase Order Reference</Translate>
            </span>
          </dt>
          <dd>{documentEntity.purchaseOrderReference}</dd>
          <dt>
            <span id="purchaseOrderDescription">
              <Translate contentKey="eInvoicesApp.document.purchaseOrderDescription">Purchase Order Description</Translate>
            </span>
          </dt>
          <dd>{documentEntity.purchaseOrderDescription}</dd>
          <dt>
            <span id="salesOrderReference">
              <Translate contentKey="eInvoicesApp.document.salesOrderReference">Sales Order Reference</Translate>
            </span>
          </dt>
          <dd>{documentEntity.salesOrderReference}</dd>
          <dt>
            <span id="salesOrderDescription">
              <Translate contentKey="eInvoicesApp.document.salesOrderDescription">Sales Order Description</Translate>
            </span>
          </dt>
          <dd>{documentEntity.salesOrderDescription}</dd>
          <dt>
            <span id="proformaInvoiceNumber">
              <Translate contentKey="eInvoicesApp.document.proformaInvoiceNumber">Proforma Invoice Number</Translate>
            </span>
          </dt>
          <dd>{documentEntity.proformaInvoiceNumber}</dd>
          <dt>
            <span id="totalSalesAmount">
              <Translate contentKey="eInvoicesApp.document.totalSalesAmount">Total Sales Amount</Translate>
            </span>
          </dt>
          <dd>{documentEntity.totalSalesAmount}</dd>
          <dt>
            <span id="totalDiscountAmount">
              <Translate contentKey="eInvoicesApp.document.totalDiscountAmount">Total Discount Amount</Translate>
            </span>
          </dt>
          <dd>{documentEntity.totalDiscountAmount}</dd>
          <dt>
            <span id="netAmount">
              <Translate contentKey="eInvoicesApp.document.netAmount">Net Amount</Translate>
            </span>
          </dt>
          <dd>{documentEntity.netAmount}</dd>
          <dt>
            <span id="extraDiscountAmount">
              <Translate contentKey="eInvoicesApp.document.extraDiscountAmount">Extra Discount Amount</Translate>
            </span>
          </dt>
          <dd>{documentEntity.extraDiscountAmount}</dd>
          <dt>
            <span id="totalItemsDiscountAmount">
              <Translate contentKey="eInvoicesApp.document.totalItemsDiscountAmount">Total Items Discount Amount</Translate>
            </span>
          </dt>
          <dd>{documentEntity.totalItemsDiscountAmount}</dd>
          <dt>
            <span id="totalAmount">
              <Translate contentKey="eInvoicesApp.document.totalAmount">Total Amount</Translate>
            </span>
          </dt>
          <dd>{documentEntity.totalAmount}</dd>
          <dt>
            <Translate contentKey="eInvoicesApp.document.issuer">Issuer</Translate>
          </dt>
          <dd>{documentEntity.issuer ? documentEntity.issuer.id : ''}</dd>
          <dt>
            <Translate contentKey="eInvoicesApp.document.receiver">Receiver</Translate>
          </dt>
          <dd>{documentEntity.receiver ? documentEntity.receiver.id : ''}</dd>
          <dt>
            <Translate contentKey="eInvoicesApp.document.payment">Payment</Translate>
          </dt>
          <dd>{documentEntity.payment ? documentEntity.payment.id : ''}</dd>
          <dt>
            <Translate contentKey="eInvoicesApp.document.delivery">Delivery</Translate>
          </dt>
          <dd>{documentEntity.delivery ? documentEntity.delivery.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/document" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/document/${documentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DocumentDetail;
