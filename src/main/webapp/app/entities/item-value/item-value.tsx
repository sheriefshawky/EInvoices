import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './item-value.reducer';
import { IItemValue } from 'app/shared/model/item-value.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ItemValue = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const itemValueList = useAppSelector(state => state.itemValue.entities);
  const loading = useAppSelector(state => state.itemValue.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="item-value-heading" data-cy="ItemValueHeading">
        <Translate contentKey="eInvoicesApp.itemValue.home.title">Item Values</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eInvoicesApp.itemValue.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eInvoicesApp.itemValue.home.createLabel">Create new Item Value</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {itemValueList && itemValueList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="eInvoicesApp.itemValue.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.itemValue.currencySold">Currency Sold</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.itemValue.amountEGP">Amount EGP</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.itemValue.amountSold">Amount Sold</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.itemValue.currencyExchangeRate">Currency Exchange Rate</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {itemValueList.map((itemValue, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${itemValue.id}`} color="link" size="sm">
                      {itemValue.id}
                    </Button>
                  </td>
                  <td>{itemValue.currencySold}</td>
                  <td>{itemValue.amountEGP}</td>
                  <td>{itemValue.amountSold}</td>
                  <td>{itemValue.currencyExchangeRate}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${itemValue.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${itemValue.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${itemValue.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="eInvoicesApp.itemValue.home.notFound">No Item Values found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ItemValue;
