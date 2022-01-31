import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './delivery.reducer';
import { IDelivery } from 'app/shared/model/delivery.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Delivery = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const deliveryList = useAppSelector(state => state.delivery.entities);
  const loading = useAppSelector(state => state.delivery.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="delivery-heading" data-cy="DeliveryHeading">
        <Translate contentKey="eInvoicesApp.delivery.home.title">Deliveries</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eInvoicesApp.delivery.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eInvoicesApp.delivery.home.createLabel">Create new Delivery</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {deliveryList && deliveryList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="eInvoicesApp.delivery.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.delivery.approach">Approach</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.delivery.packaging">Packaging</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.delivery.dateValidity">Date Validity</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.delivery.exportPort">Export Port</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.delivery.countryOfOrigin">Country Of Origin</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.delivery.grossWeight">Gross Weight</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.delivery.netWeight">Net Weight</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.delivery.terms">Terms</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {deliveryList.map((delivery, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${delivery.id}`} color="link" size="sm">
                      {delivery.id}
                    </Button>
                  </td>
                  <td>{delivery.approach}</td>
                  <td>{delivery.packaging}</td>
                  <td>{delivery.dateValidity}</td>
                  <td>{delivery.exportPort}</td>
                  <td>{delivery.countryOfOrigin}</td>
                  <td>{delivery.grossWeight}</td>
                  <td>{delivery.netWeight}</td>
                  <td>{delivery.terms}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${delivery.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${delivery.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${delivery.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="eInvoicesApp.delivery.home.notFound">No Deliveries found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Delivery;
