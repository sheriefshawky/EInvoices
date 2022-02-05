import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './workflow-parameters.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const WorkflowParametersDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const workflowParametersEntity = useAppSelector(state => state.workflowParameters.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="workflowParametersDetailsHeading">
          <Translate contentKey="eInvoicesApp.workflowParameters.detail.title">WorkflowParameters</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{workflowParametersEntity.id}</dd>
          <dt>
            <span id="parameter">
              <Translate contentKey="eInvoicesApp.workflowParameters.parameter">Parameter</Translate>
            </span>
          </dt>
          <dd>{workflowParametersEntity.parameter}</dd>
          <dt>
            <span id="wfValue">
              <Translate contentKey="eInvoicesApp.workflowParameters.wfValue">Wf Value</Translate>
            </span>
          </dt>
          <dd>{workflowParametersEntity.wfValue}</dd>
          <dt>
            <span id="activeFrom">
              <Translate contentKey="eInvoicesApp.workflowParameters.activeFrom">Active From</Translate>
            </span>
          </dt>
          <dd>
            {workflowParametersEntity.activeFrom ? (
              <TextFormat value={workflowParametersEntity.activeFrom} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="activeTo">
              <Translate contentKey="eInvoicesApp.workflowParameters.activeTo">Active To</Translate>
            </span>
          </dt>
          <dd>
            {workflowParametersEntity.activeTo ? (
              <TextFormat value={workflowParametersEntity.activeTo} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="eInvoicesApp.workflowParameters.documentTypeVersion">Document Type Version</Translate>
          </dt>
          <dd>{workflowParametersEntity.documentTypeVersion ? workflowParametersEntity.documentTypeVersion.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/workflow-parameters" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/workflow-parameters/${workflowParametersEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default WorkflowParametersDetail;
