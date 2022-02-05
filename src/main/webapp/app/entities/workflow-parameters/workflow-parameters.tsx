import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './workflow-parameters.reducer';
import { IWorkflowParameters } from 'app/shared/model/workflow-parameters.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const WorkflowParameters = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const workflowParametersList = useAppSelector(state => state.workflowParameters.entities);
  const loading = useAppSelector(state => state.workflowParameters.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="workflow-parameters-heading" data-cy="WorkflowParametersHeading">
        <Translate contentKey="eInvoicesApp.workflowParameters.home.title">Workflow Parameters</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eInvoicesApp.workflowParameters.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eInvoicesApp.workflowParameters.home.createLabel">Create new Workflow Parameters</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {workflowParametersList && workflowParametersList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="eInvoicesApp.workflowParameters.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.workflowParameters.parameter">Parameter</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.workflowParameters.wfValue">Wf Value</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.workflowParameters.activeFrom">Active From</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.workflowParameters.activeTo">Active To</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.workflowParameters.documentTypeVersion">Document Type Version</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {workflowParametersList.map((workflowParameters, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${workflowParameters.id}`} color="link" size="sm">
                      {workflowParameters.id}
                    </Button>
                  </td>
                  <td>{workflowParameters.parameter}</td>
                  <td>{workflowParameters.wfValue}</td>
                  <td>
                    {workflowParameters.activeFrom ? (
                      <TextFormat type="date" value={workflowParameters.activeFrom} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {workflowParameters.activeTo ? (
                      <TextFormat type="date" value={workflowParameters.activeTo} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {workflowParameters.documentTypeVersion ? (
                      <Link to={`document-type-version/${workflowParameters.documentTypeVersion.id}`}>
                        {workflowParameters.documentTypeVersion.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${workflowParameters.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${workflowParameters.id}/edit`}
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
                        to={`${match.url}/${workflowParameters.id}/delete`}
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
              <Translate contentKey="eInvoicesApp.workflowParameters.home.notFound">No Workflow Parameters found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default WorkflowParameters;
