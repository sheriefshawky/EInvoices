import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './signature.reducer';
import { ISignature } from 'app/shared/model/signature.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Signature = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const signatureList = useAppSelector(state => state.signature.entities);
  const loading = useAppSelector(state => state.signature.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="signature-heading" data-cy="SignatureHeading">
        <Translate contentKey="eInvoicesApp.signature.home.title">Signatures</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eInvoicesApp.signature.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eInvoicesApp.signature.home.createLabel">Create new Signature</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {signatureList && signatureList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="eInvoicesApp.signature.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.signature.type">Type</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.signature.sigValue">Sig Value</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.signature.document">Document</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {signatureList.map((signature, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${signature.id}`} color="link" size="sm">
                      {signature.id}
                    </Button>
                  </td>
                  <td>{signature.type}</td>
                  <td>{signature.sigValue}</td>
                  <td>{signature.document ? <Link to={`document/${signature.document.id}`}>{signature.document.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${signature.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${signature.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${signature.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="eInvoicesApp.signature.home.notFound">No Signatures found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Signature;
