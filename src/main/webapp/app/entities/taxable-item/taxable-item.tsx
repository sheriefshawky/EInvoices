import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './taxable-item.reducer';
import { ITaxableItem } from 'app/shared/model/taxable-item.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TaxableItem = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const taxableItemList = useAppSelector(state => state.taxableItem.entities);
  const loading = useAppSelector(state => state.taxableItem.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="taxable-item-heading" data-cy="TaxableItemHeading">
        <Translate contentKey="eInvoicesApp.taxableItem.home.title">Taxable Items</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eInvoicesApp.taxableItem.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eInvoicesApp.taxableItem.home.createLabel">Create new Taxable Item</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {taxableItemList && taxableItemList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="eInvoicesApp.taxableItem.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.taxableItem.taxType">Tax Type</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.taxableItem.amount">Amount</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.taxableItem.subType">Sub Type</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.taxableItem.rate">Rate</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.taxableItem.invoiceLine">Invoice Line</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {taxableItemList.map((taxableItem, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${taxableItem.id}`} color="link" size="sm">
                      {taxableItem.id}
                    </Button>
                  </td>
                  <td>{taxableItem.taxType}</td>
                  <td>{taxableItem.amount}</td>
                  <td>{taxableItem.subType}</td>
                  <td>{taxableItem.rate}</td>
                  <td>
                    {taxableItem.invoiceLine ? (
                      <Link to={`invoice-line/${taxableItem.invoiceLine.id}`}>{taxableItem.invoiceLine.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${taxableItem.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${taxableItem.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${taxableItem.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="eInvoicesApp.taxableItem.home.notFound">No Taxable Items found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default TaxableItem;
