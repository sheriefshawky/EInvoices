import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './document-type-version.reducer';
import { IDocumentTypeVersion } from 'app/shared/model/document-type-version.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const DocumentTypeVersion = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const documentTypeVersionList = useAppSelector(state => state.documentTypeVersion.entities);
  const loading = useAppSelector(state => state.documentTypeVersion.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="document-type-version-heading" data-cy="DocumentTypeVersionHeading">
        <Translate contentKey="eInvoicesApp.documentTypeVersion.home.title">Document Type Versions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eInvoicesApp.documentTypeVersion.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eInvoicesApp.documentTypeVersion.home.createLabel">Create new Document Type Version</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {documentTypeVersionList && documentTypeVersionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="eInvoicesApp.documentTypeVersion.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.documentTypeVersion.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.documentTypeVersion.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.documentTypeVersion.versionNumber">Version Number</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.documentTypeVersion.status">Status</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.documentTypeVersion.activeFrom">Active From</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.documentTypeVersion.activeTo">Active To</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.documentTypeVersion.documentType">Document Type</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {documentTypeVersionList.map((documentTypeVersion, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${documentTypeVersion.id}`} color="link" size="sm">
                      {documentTypeVersion.id}
                    </Button>
                  </td>
                  <td>{documentTypeVersion.name}</td>
                  <td>{documentTypeVersion.description}</td>
                  <td>{documentTypeVersion.versionNumber}</td>
                  <td>{documentTypeVersion.status}</td>
                  <td>
                    {documentTypeVersion.activeFrom ? (
                      <TextFormat type="date" value={documentTypeVersion.activeFrom} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {documentTypeVersion.activeTo ? (
                      <TextFormat type="date" value={documentTypeVersion.activeTo} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {documentTypeVersion.documentType ? (
                      <Link to={`document-type/${documentTypeVersion.documentType.id}`}>{documentTypeVersion.documentType.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${documentTypeVersion.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${documentTypeVersion.id}/edit`}
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
                        to={`${match.url}/${documentTypeVersion.id}/delete`}
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
              <Translate contentKey="eInvoicesApp.documentTypeVersion.home.notFound">No Document Type Versions found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default DocumentTypeVersion;
