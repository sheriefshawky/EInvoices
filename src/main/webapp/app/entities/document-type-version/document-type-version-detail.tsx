import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './document-type-version.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const DocumentTypeVersionDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const documentTypeVersionEntity = useAppSelector(state => state.documentTypeVersion.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="documentTypeVersionDetailsHeading">
          <Translate contentKey="eInvoicesApp.documentTypeVersion.detail.title">DocumentTypeVersion</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{documentTypeVersionEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="eInvoicesApp.documentTypeVersion.name">Name</Translate>
            </span>
          </dt>
          <dd>{documentTypeVersionEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="eInvoicesApp.documentTypeVersion.description">Description</Translate>
            </span>
          </dt>
          <dd>{documentTypeVersionEntity.description}</dd>
          <dt>
            <span id="versionNumber">
              <Translate contentKey="eInvoicesApp.documentTypeVersion.versionNumber">Version Number</Translate>
            </span>
          </dt>
          <dd>{documentTypeVersionEntity.versionNumber}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="eInvoicesApp.documentTypeVersion.status">Status</Translate>
            </span>
          </dt>
          <dd>{documentTypeVersionEntity.status}</dd>
          <dt>
            <span id="activeFrom">
              <Translate contentKey="eInvoicesApp.documentTypeVersion.activeFrom">Active From</Translate>
            </span>
          </dt>
          <dd>
            {documentTypeVersionEntity.activeFrom ? (
              <TextFormat value={documentTypeVersionEntity.activeFrom} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="activeTo">
              <Translate contentKey="eInvoicesApp.documentTypeVersion.activeTo">Active To</Translate>
            </span>
          </dt>
          <dd>
            {documentTypeVersionEntity.activeTo ? (
              <TextFormat value={documentTypeVersionEntity.activeTo} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="eInvoicesApp.documentTypeVersion.documentType">Document Type</Translate>
          </dt>
          <dd>{documentTypeVersionEntity.documentType ? documentTypeVersionEntity.documentType.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/document-type-version" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/document-type-version/${documentTypeVersionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DocumentTypeVersionDetail;
