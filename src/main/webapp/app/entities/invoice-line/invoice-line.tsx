import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './invoice-line.reducer';
import { IInvoiceLine } from 'app/shared/model/invoice-line.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const InvoiceLine = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const invoiceLineList = useAppSelector(state => state.invoiceLine.entities);
  const loading = useAppSelector(state => state.invoiceLine.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="invoice-line-heading" data-cy="InvoiceLineHeading">
        <Translate contentKey="eInvoicesApp.invoiceLine.home.title">Invoice Lines</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eInvoicesApp.invoiceLine.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eInvoicesApp.invoiceLine.home.createLabel">Create new Invoice Line</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {invoiceLineList && invoiceLineList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="eInvoicesApp.invoiceLine.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.invoiceLine.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.invoiceLine.itemType">Item Type</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.invoiceLine.itemCode">Item Code</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.invoiceLine.unitType">Unit Type</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.invoiceLine.quantity">Quantity</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.invoiceLine.salesTotal">Sales Total</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.invoiceLine.total">Total</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.invoiceLine.valueDifference">Value Difference</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.invoiceLine.totalTaxableFees">Total Taxable Fees</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.invoiceLine.netTotal">Net Total</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.invoiceLine.itemsDiscount">Items Discount</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.invoiceLine.internalCode">Internal Code</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.invoiceLine.unitValue">Unit Value</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.invoiceLine.discount">Discount</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.invoiceLine.document">Document</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {invoiceLineList.map((invoiceLine, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${invoiceLine.id}`} color="link" size="sm">
                      {invoiceLine.id}
                    </Button>
                  </td>
                  <td>{invoiceLine.description}</td>
                  <td>{invoiceLine.itemType}</td>
                  <td>{invoiceLine.itemCode}</td>
                  <td>{invoiceLine.unitType}</td>
                  <td>{invoiceLine.quantity}</td>
                  <td>{invoiceLine.salesTotal}</td>
                  <td>{invoiceLine.total}</td>
                  <td>{invoiceLine.valueDifference}</td>
                  <td>{invoiceLine.totalTaxableFees}</td>
                  <td>{invoiceLine.netTotal}</td>
                  <td>{invoiceLine.itemsDiscount}</td>
                  <td>{invoiceLine.internalCode}</td>
                  <td>
                    {invoiceLine.unitValue ? <Link to={`item-value/${invoiceLine.unitValue.id}`}>{invoiceLine.unitValue.id}</Link> : ''}
                  </td>
                  <td>{invoiceLine.discount ? <Link to={`discount/${invoiceLine.discount.id}`}>{invoiceLine.discount.id}</Link> : ''}</td>
                  <td>{invoiceLine.document ? <Link to={`document/${invoiceLine.document.id}`}>{invoiceLine.document.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${invoiceLine.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${invoiceLine.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${invoiceLine.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="eInvoicesApp.invoiceLine.home.notFound">No Invoice Lines found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default InvoiceLine;
