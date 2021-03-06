import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './receiver.reducer';
import { IReceiver } from 'app/shared/model/receiver.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Receiver = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const receiverList = useAppSelector(state => state.receiver.entities);
  const loading = useAppSelector(state => state.receiver.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="receiver-heading" data-cy="ReceiverHeading">
        <Translate contentKey="eInvoicesApp.receiver.home.title">Receivers</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eInvoicesApp.receiver.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eInvoicesApp.receiver.home.createLabel">Create new Receiver</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {receiverList && receiverList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="eInvoicesApp.receiver.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.receiver.recieverType">Reciever Type</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.receiver.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.receiver.address">Address</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {receiverList.map((receiver, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${receiver.id}`} color="link" size="sm">
                      {receiver.id}
                    </Button>
                  </td>
                  <td>{receiver.recieverType}</td>
                  <td>{receiver.name}</td>
                  <td>{receiver.address ? <Link to={`receiver-address/${receiver.address.id}`}>{receiver.address.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${receiver.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${receiver.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${receiver.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="eInvoicesApp.receiver.home.notFound">No Receivers found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Receiver;
