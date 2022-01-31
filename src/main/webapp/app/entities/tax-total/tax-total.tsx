import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './tax-total.reducer';
import { ITaxTotal } from 'app/shared/model/tax-total.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TaxTotal = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const taxTotalList = useAppSelector(state => state.taxTotal.entities);
  const loading = useAppSelector(state => state.taxTotal.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="tax-total-heading" data-cy="TaxTotalHeading">
        <Translate contentKey="eInvoicesApp.taxTotal.home.title">Tax Totals</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eInvoicesApp.taxTotal.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eInvoicesApp.taxTotal.home.createLabel">Create new Tax Total</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {taxTotalList && taxTotalList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="eInvoicesApp.taxTotal.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.taxTotal.taxType">Tax Type</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.taxTotal.amount">Amount</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.taxTotal.document">Document</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {taxTotalList.map((taxTotal, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${taxTotal.id}`} color="link" size="sm">
                      {taxTotal.id}
                    </Button>
                  </td>
                  <td>{taxTotal.taxType}</td>
                  <td>{taxTotal.amount}</td>
                  <td>{taxTotal.document ? <Link to={`document/${taxTotal.document.id}`}>{taxTotal.document.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${taxTotal.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${taxTotal.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${taxTotal.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="eInvoicesApp.taxTotal.home.notFound">No Tax Totals found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default TaxTotal;
