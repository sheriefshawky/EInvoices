import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './signature.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SignatureDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const signatureEntity = useAppSelector(state => state.signature.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="signatureDetailsHeading">
          <Translate contentKey="eInvoicesApp.signature.detail.title">Signature</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{signatureEntity.id}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="eInvoicesApp.signature.type">Type</Translate>
            </span>
          </dt>
          <dd>{signatureEntity.type}</dd>
          <dt>
            <span id="sigValue">
              <Translate contentKey="eInvoicesApp.signature.sigValue">Sig Value</Translate>
            </span>
          </dt>
          <dd>{signatureEntity.sigValue}</dd>
          <dt>
            <Translate contentKey="eInvoicesApp.signature.document">Document</Translate>
          </dt>
          <dd>{signatureEntity.document ? signatureEntity.document.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/signature" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/signature/${signatureEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SignatureDetail;
