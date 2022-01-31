import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './issuer-address.reducer';
import { IIssuerAddress } from 'app/shared/model/issuer-address.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const IssuerAddress = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const issuerAddressList = useAppSelector(state => state.issuerAddress.entities);
  const loading = useAppSelector(state => state.issuerAddress.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="issuer-address-heading" data-cy="IssuerAddressHeading">
        <Translate contentKey="eInvoicesApp.issuerAddress.home.title">Issuer Addresses</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eInvoicesApp.issuerAddress.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eInvoicesApp.issuerAddress.home.createLabel">Create new Issuer Address</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {issuerAddressList && issuerAddressList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="eInvoicesApp.issuerAddress.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.issuerAddress.branchId">Branch Id</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.issuerAddress.country">Country</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.issuerAddress.governate">Governate</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.issuerAddress.regionCity">Region City</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.issuerAddress.street">Street</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.issuerAddress.buildingNumber">Building Number</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.issuerAddress.postalCode">Postal Code</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.issuerAddress.floor">Floor</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.issuerAddress.room">Room</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.issuerAddress.landmark">Landmark</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.issuerAddress.additionalInformation">Additional Information</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {issuerAddressList.map((issuerAddress, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${issuerAddress.id}`} color="link" size="sm">
                      {issuerAddress.id}
                    </Button>
                  </td>
                  <td>{issuerAddress.branchId}</td>
                  <td>{issuerAddress.country}</td>
                  <td>{issuerAddress.governate}</td>
                  <td>{issuerAddress.regionCity}</td>
                  <td>{issuerAddress.street}</td>
                  <td>{issuerAddress.buildingNumber}</td>
                  <td>{issuerAddress.postalCode}</td>
                  <td>{issuerAddress.floor}</td>
                  <td>{issuerAddress.room}</td>
                  <td>{issuerAddress.landmark}</td>
                  <td>{issuerAddress.additionalInformation}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${issuerAddress.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${issuerAddress.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${issuerAddress.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
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
              <Translate contentKey="eInvoicesApp.issuerAddress.home.notFound">No Issuer Addresses found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default IssuerAddress;
