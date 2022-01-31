import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './document.reducer';
import { IDocument } from 'app/shared/model/document.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Document = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const documentList = useAppSelector(state => state.document.entities);
  const loading = useAppSelector(state => state.document.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="document-heading" data-cy="DocumentHeading">
        <Translate contentKey="eInvoicesApp.document.home.title">Documents</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eInvoicesApp.document.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eInvoicesApp.document.home.createLabel">Create new Document</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {documentList && documentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="eInvoicesApp.document.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.document.documentType">Document Type</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.document.documentTypeVersion">Document Type Version</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.document.dateTimeIssued">Date Time Issued</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.document.taxpayerActivityCode">Taxpayer Activity Code</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.document.internalId">Internal Id</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.document.purchaseOrderReference">Purchase Order Reference</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.document.purchaseOrderDescription">Purchase Order Description</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.document.salesOrderReference">Sales Order Reference</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.document.salesOrderDescription">Sales Order Description</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.document.proformaInvoiceNumber">Proforma Invoice Number</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.document.totalSalesAmount">Total Sales Amount</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.document.totalDiscountAmount">Total Discount Amount</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.document.netAmount">Net Amount</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.document.extraDiscountAmount">Extra Discount Amount</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.document.totalItemsDiscountAmount">Total Items Discount Amount</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.document.totalAmount">Total Amount</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.document.issuer">Issuer</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.document.receiver">Receiver</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.document.payment">Payment</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.document.delivery">Delivery</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {documentList.map((document, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${document.id}`} color="link" size="sm">
                      {document.id}
                    </Button>
                  </td>
                  <td>{document.documentType}</td>
                  <td>{document.documentTypeVersion}</td>
                  <td>
                    {document.dateTimeIssued ? <TextFormat type="date" value={document.dateTimeIssued} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{document.taxpayerActivityCode}</td>
                  <td>{document.internalId}</td>
                  <td>{document.purchaseOrderReference}</td>
                  <td>{document.purchaseOrderDescription}</td>
                  <td>{document.salesOrderReference}</td>
                  <td>{document.salesOrderDescription}</td>
                  <td>{document.proformaInvoiceNumber}</td>
                  <td>{document.totalSalesAmount}</td>
                  <td>{document.totalDiscountAmount}</td>
                  <td>{document.netAmount}</td>
                  <td>{document.extraDiscountAmount}</td>
                  <td>{document.totalItemsDiscountAmount}</td>
                  <td>{document.totalAmount}</td>
                  <td>{document.issuer ? <Link to={`issuer/${document.issuer.id}`}>{document.issuer.id}</Link> : ''}</td>
                  <td>{document.receiver ? <Link to={`receiver/${document.receiver.id}`}>{document.receiver.id}</Link> : ''}</td>
                  <td>{document.payment ? <Link to={`payment/${document.payment.id}`}>{document.payment.id}</Link> : ''}</td>
                  <td>{document.delivery ? <Link to={`delivery/${document.delivery.id}`}>{document.delivery.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${document.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${document.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${document.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="eInvoicesApp.document.home.notFound">No Documents found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Document;
