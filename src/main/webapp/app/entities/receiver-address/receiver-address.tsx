import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './receiver-address.reducer';
import { IReceiverAddress } from 'app/shared/model/receiver-address.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ReceiverAddress = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const receiverAddressList = useAppSelector(state => state.receiverAddress.entities);
  const loading = useAppSelector(state => state.receiverAddress.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="receiver-address-heading" data-cy="ReceiverAddressHeading">
        <Translate contentKey="eInvoicesApp.receiverAddress.home.title">Receiver Addresses</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eInvoicesApp.receiverAddress.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eInvoicesApp.receiverAddress.home.createLabel">Create new Receiver Address</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {receiverAddressList && receiverAddressList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="eInvoicesApp.receiverAddress.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.receiverAddress.country">Country</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.receiverAddress.governate">Governate</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.receiverAddress.regionCity">Region City</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.receiverAddress.street">Street</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.receiverAddress.buildingNumber">Building Number</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.receiverAddress.postalCode">Postal Code</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.receiverAddress.floor">Floor</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.receiverAddress.room">Room</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.receiverAddress.landmark">Landmark</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.receiverAddress.additionalInformation">Additional Information</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {receiverAddressList.map((receiverAddress, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${receiverAddress.id}`} color="link" size="sm">
                      {receiverAddress.id}
                    </Button>
                  </td>
                  <td>{receiverAddress.country}</td>
                  <td>{receiverAddress.governate}</td>
                  <td>{receiverAddress.regionCity}</td>
                  <td>{receiverAddress.street}</td>
                  <td>{receiverAddress.buildingNumber}</td>
                  <td>{receiverAddress.postalCode}</td>
                  <td>{receiverAddress.floor}</td>
                  <td>{receiverAddress.room}</td>
                  <td>{receiverAddress.landmark}</td>
                  <td>{receiverAddress.additionalInformation}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${receiverAddress.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${receiverAddress.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${receiverAddress.id}/delete`}
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
              <Translate contentKey="eInvoicesApp.receiverAddress.home.notFound">No Receiver Addresses found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ReceiverAddress;
