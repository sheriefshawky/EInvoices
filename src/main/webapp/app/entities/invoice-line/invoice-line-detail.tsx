import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './invoice-line.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const InvoiceLineDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const invoiceLineEntity = useAppSelector(state => state.invoiceLine.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="invoiceLineDetailsHeading">
          <Translate contentKey="eInvoicesApp.invoiceLine.detail.title">InvoiceLine</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{invoiceLineEntity.id}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="eInvoicesApp.invoiceLine.description">Description</Translate>
            </span>
          </dt>
          <dd>{invoiceLineEntity.description}</dd>
          <dt>
            <span id="itemType">
              <Translate contentKey="eInvoicesApp.invoiceLine.itemType">Item Type</Translate>
            </span>
          </dt>
          <dd>{invoiceLineEntity.itemType}</dd>
          <dt>
            <span id="itemCode">
              <Translate contentKey="eInvoicesApp.invoiceLine.itemCode">Item Code</Translate>
            </span>
          </dt>
          <dd>{invoiceLineEntity.itemCode}</dd>
          <dt>
            <span id="unitType">
              <Translate contentKey="eInvoicesApp.invoiceLine.unitType">Unit Type</Translate>
            </span>
          </dt>
          <dd>{invoiceLineEntity.unitType}</dd>
          <dt>
            <span id="quantity">
              <Translate contentKey="eInvoicesApp.invoiceLine.quantity">Quantity</Translate>
            </span>
          </dt>
          <dd>{invoiceLineEntity.quantity}</dd>
          <dt>
            <span id="salesTotal">
              <Translate contentKey="eInvoicesApp.invoiceLine.salesTotal">Sales Total</Translate>
            </span>
          </dt>
          <dd>{invoiceLineEntity.salesTotal}</dd>
          <dt>
            <span id="total">
              <Translate contentKey="eInvoicesApp.invoiceLine.total">Total</Translate>
            </span>
          </dt>
          <dd>{invoiceLineEntity.total}</dd>
          <dt>
            <span id="valueDifference">
              <Translate contentKey="eInvoicesApp.invoiceLine.valueDifference">Value Difference</Translate>
            </span>
          </dt>
          <dd>{invoiceLineEntity.valueDifference}</dd>
          <dt>
            <span id="totalTaxableFees">
              <Translate contentKey="eInvoicesApp.invoiceLine.totalTaxableFees">Total Taxable Fees</Translate>
            </span>
          </dt>
          <dd>{invoiceLineEntity.totalTaxableFees}</dd>
          <dt>
            <span id="netTotal">
              <Translate contentKey="eInvoicesApp.invoiceLine.netTotal">Net Total</Translate>
            </span>
          </dt>
          <dd>{invoiceLineEntity.netTotal}</dd>
          <dt>
            <span id="itemsDiscount">
              <Translate contentKey="eInvoicesApp.invoiceLine.itemsDiscount">Items Discount</Translate>
            </span>
          </dt>
          <dd>{invoiceLineEntity.itemsDiscount}</dd>
          <dt>
            <span id="internalCode">
              <Translate contentKey="eInvoicesApp.invoiceLine.internalCode">Internal Code</Translate>
            </span>
          </dt>
          <dd>{invoiceLineEntity.internalCode}</dd>
          <dt>
            <Translate contentKey="eInvoicesApp.invoiceLine.unitValue">Unit Value</Translate>
          </dt>
          <dd>{invoiceLineEntity.unitValue ? invoiceLineEntity.unitValue.id : ''}</dd>
          <dt>
            <Translate contentKey="eInvoicesApp.invoiceLine.discount">Discount</Translate>
          </dt>
          <dd>{invoiceLineEntity.discount ? invoiceLineEntity.discount.id : ''}</dd>
          <dt>
            <Translate contentKey="eInvoicesApp.invoiceLine.document">Document</Translate>
          </dt>
          <dd>{invoiceLineEntity.document ? invoiceLineEntity.document.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/invoice-line" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/invoice-line/${invoiceLineEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default InvoiceLineDetail;
