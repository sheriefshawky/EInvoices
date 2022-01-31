import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './value.reducer';
import { IValue } from 'app/shared/model/value.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Value = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const valueList = useAppSelector(state => state.value.entities);
  const loading = useAppSelector(state => state.value.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="value-heading" data-cy="ValueHeading">
        <Translate contentKey="eInvoicesApp.value.home.title">Values</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eInvoicesApp.value.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eInvoicesApp.value.home.createLabel">Create new Value</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {valueList && valueList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="eInvoicesApp.value.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.value.currencySold">Currency Sold</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.value.amountEGP">Amount EGP</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.value.amountSold">Amount Sold</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.value.currencyExchangeRate">Currency Exchange Rate</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {valueList.map((value, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${value.id}`} color="link" size="sm">
                      {value.id}
                    </Button>
                  </td>
                  <td>{value.currencySold}</td>
                  <td>{value.amountEGP}</td>
                  <td>{value.amountSold}</td>
                  <td>{value.currencyExchangeRate}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${value.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${value.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${value.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="eInvoicesApp.value.home.notFound">No Values found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Value;
