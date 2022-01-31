import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './document-type.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const DocumentTypeDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const documentTypeEntity = useAppSelector(state => state.documentType.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="documentTypeDetailsHeading">
          <Translate contentKey="eInvoicesApp.documentType.detail.title">DocumentType</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{documentTypeEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="eInvoicesApp.documentType.name">Name</Translate>
            </span>
          </dt>
          <dd>{documentTypeEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="eInvoicesApp.documentType.description">Description</Translate>
            </span>
          </dt>
          <dd>{documentTypeEntity.description}</dd>
          <dt>
            <span id="activeFrom">
              <Translate contentKey="eInvoicesApp.documentType.activeFrom">Active From</Translate>
            </span>
          </dt>
          <dd>
            {documentTypeEntity.activeFrom ? (
              <TextFormat value={documentTypeEntity.activeFrom} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="activeTo">
              <Translate contentKey="eInvoicesApp.documentType.activeTo">Active To</Translate>
            </span>
          </dt>
          <dd>
            {documentTypeEntity.activeTo ? <TextFormat value={documentTypeEntity.activeTo} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/document-type" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/document-type/${documentTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DocumentTypeDetail;
