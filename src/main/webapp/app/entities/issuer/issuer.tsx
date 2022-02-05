import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './issuer.reducer';
import { IIssuer } from 'app/shared/model/issuer.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Issuer = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const issuerList = useAppSelector(state => state.issuer.entities);
  const loading = useAppSelector(state => state.issuer.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="issuer-heading" data-cy="IssuerHeading">
        <Translate contentKey="eInvoicesApp.issuer.home.title">Issuers</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eInvoicesApp.issuer.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eInvoicesApp.issuer.home.createLabel">Create new Issuer</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {issuerList && issuerList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="eInvoicesApp.issuer.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.issuer.issuertype">Issuertype</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.issuer.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.issuer.address">Address</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {issuerList.map((issuer, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${issuer.id}`} color="link" size="sm">
                      {issuer.id}
                    </Button>
                  </td>
                  <td>{issuer.issuertype}</td>
                  <td>{issuer.name}</td>
                  <td>{issuer.address ? <Link to={`issuer-address/${issuer.address.id}`}>{issuer.address.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${issuer.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${issuer.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${issuer.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="eInvoicesApp.issuer.home.notFound">No Issuers found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Issuer;
