import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './document-type.reducer';
import { IDocumentType } from 'app/shared/model/document-type.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const DocumentType = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const documentTypeList = useAppSelector(state => state.documentType.entities);
  const loading = useAppSelector(state => state.documentType.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="document-type-heading" data-cy="DocumentTypeHeading">
        <Translate contentKey="eInvoicesApp.documentType.home.title">Document Types</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eInvoicesApp.documentType.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eInvoicesApp.documentType.home.createLabel">Create new Document Type</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {documentTypeList && documentTypeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="eInvoicesApp.documentType.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.documentType.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.documentType.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.documentType.activeFrom">Active From</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.documentType.activeTo">Active To</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {documentTypeList.map((documentType, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${documentType.id}`} color="link" size="sm">
                      {documentType.id}
                    </Button>
                  </td>
                  <td>{documentType.name}</td>
                  <td>{documentType.description}</td>
                  <td>
                    {documentType.activeFrom ? <TextFormat type="date" value={documentType.activeFrom} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {documentType.activeTo ? <TextFormat type="date" value={documentType.activeTo} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${documentType.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${documentType.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${documentType.id}/delete`}
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
              <Translate contentKey="eInvoicesApp.documentType.home.notFound">No Document Types found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default DocumentType;
