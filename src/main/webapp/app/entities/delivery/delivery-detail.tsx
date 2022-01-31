import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './delivery.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const DeliveryDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const deliveryEntity = useAppSelector(state => state.delivery.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="deliveryDetailsHeading">
          <Translate contentKey="eInvoicesApp.delivery.detail.title">Delivery</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{deliveryEntity.id}</dd>
          <dt>
            <span id="approach">
              <Translate contentKey="eInvoicesApp.delivery.approach">Approach</Translate>
            </span>
          </dt>
          <dd>{deliveryEntity.approach}</dd>
          <dt>
            <span id="packaging">
              <Translate contentKey="eInvoicesApp.delivery.packaging">Packaging</Translate>
            </span>
          </dt>
          <dd>{deliveryEntity.packaging}</dd>
          <dt>
            <span id="dateValidity">
              <Translate contentKey="eInvoicesApp.delivery.dateValidity">Date Validity</Translate>
            </span>
          </dt>
          <dd>{deliveryEntity.dateValidity}</dd>
          <dt>
            <span id="exportPort">
              <Translate contentKey="eInvoicesApp.delivery.exportPort">Export Port</Translate>
            </span>
          </dt>
          <dd>{deliveryEntity.exportPort}</dd>
          <dt>
            <span id="countryOfOrigin">
              <Translate contentKey="eInvoicesApp.delivery.countryOfOrigin">Country Of Origin</Translate>
            </span>
          </dt>
          <dd>{deliveryEntity.countryOfOrigin}</dd>
          <dt>
            <span id="grossWeight">
              <Translate contentKey="eInvoicesApp.delivery.grossWeight">Gross Weight</Translate>
            </span>
          </dt>
          <dd>{deliveryEntity.grossWeight}</dd>
          <dt>
            <span id="netWeight">
              <Translate contentKey="eInvoicesApp.delivery.netWeight">Net Weight</Translate>
            </span>
          </dt>
          <dd>{deliveryEntity.netWeight}</dd>
          <dt>
            <span id="terms">
              <Translate contentKey="eInvoicesApp.delivery.terms">Terms</Translate>
            </span>
          </dt>
          <dd>{deliveryEntity.terms}</dd>
        </dl>
        <Button tag={Link} to="/delivery" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/delivery/${deliveryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DeliveryDetail;
