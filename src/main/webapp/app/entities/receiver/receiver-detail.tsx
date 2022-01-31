import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './receiver.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ReceiverDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const receiverEntity = useAppSelector(state => state.receiver.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="receiverDetailsHeading">
          <Translate contentKey="eInvoicesApp.receiver.detail.title">Receiver</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{receiverEntity.id}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="eInvoicesApp.receiver.type">Type</Translate>
            </span>
          </dt>
          <dd>{receiverEntity.type}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="eInvoicesApp.receiver.name">Name</Translate>
            </span>
          </dt>
          <dd>{receiverEntity.name}</dd>
          <dt>
            <Translate contentKey="eInvoicesApp.receiver.address">Address</Translate>
          </dt>
          <dd>{receiverEntity.address ? receiverEntity.address.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/receiver" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/receiver/${receiverEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ReceiverDetail;
