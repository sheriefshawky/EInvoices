import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './issuer.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const IssuerDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const issuerEntity = useAppSelector(state => state.issuer.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="issuerDetailsHeading">
          <Translate contentKey="eInvoicesApp.issuer.detail.title">Issuer</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{issuerEntity.id}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="eInvoicesApp.issuer.type">Type</Translate>
            </span>
          </dt>
          <dd>{issuerEntity.type}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="eInvoicesApp.issuer.name">Name</Translate>
            </span>
          </dt>
          <dd>{issuerEntity.name}</dd>
          <dt>
            <Translate contentKey="eInvoicesApp.issuer.address">Address</Translate>
          </dt>
          <dd>{issuerEntity.address ? issuerEntity.address.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/issuer" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/issuer/${issuerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default IssuerDetail;
