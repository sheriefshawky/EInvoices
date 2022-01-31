import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './receiver-address.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ReceiverAddressDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const receiverAddressEntity = useAppSelector(state => state.receiverAddress.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="receiverAddressDetailsHeading">
          <Translate contentKey="eInvoicesApp.receiverAddress.detail.title">ReceiverAddress</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{receiverAddressEntity.id}</dd>
          <dt>
            <span id="country">
              <Translate contentKey="eInvoicesApp.receiverAddress.country">Country</Translate>
            </span>
          </dt>
          <dd>{receiverAddressEntity.country}</dd>
          <dt>
            <span id="governate">
              <Translate contentKey="eInvoicesApp.receiverAddress.governate">Governate</Translate>
            </span>
          </dt>
          <dd>{receiverAddressEntity.governate}</dd>
          <dt>
            <span id="regionCity">
              <Translate contentKey="eInvoicesApp.receiverAddress.regionCity">Region City</Translate>
            </span>
          </dt>
          <dd>{receiverAddressEntity.regionCity}</dd>
          <dt>
            <span id="street">
              <Translate contentKey="eInvoicesApp.receiverAddress.street">Street</Translate>
            </span>
          </dt>
          <dd>{receiverAddressEntity.street}</dd>
          <dt>
            <span id="buildingNumber">
              <Translate contentKey="eInvoicesApp.receiverAddress.buildingNumber">Building Number</Translate>
            </span>
          </dt>
          <dd>{receiverAddressEntity.buildingNumber}</dd>
          <dt>
            <span id="postalCode">
              <Translate contentKey="eInvoicesApp.receiverAddress.postalCode">Postal Code</Translate>
            </span>
          </dt>
          <dd>{receiverAddressEntity.postalCode}</dd>
          <dt>
            <span id="floor">
              <Translate contentKey="eInvoicesApp.receiverAddress.floor">Floor</Translate>
            </span>
          </dt>
          <dd>{receiverAddressEntity.floor}</dd>
          <dt>
            <span id="room">
              <Translate contentKey="eInvoicesApp.receiverAddress.room">Room</Translate>
            </span>
          </dt>
          <dd>{receiverAddressEntity.room}</dd>
          <dt>
            <span id="landmark">
              <Translate contentKey="eInvoicesApp.receiverAddress.landmark">Landmark</Translate>
            </span>
          </dt>
          <dd>{receiverAddressEntity.landmark}</dd>
          <dt>
            <span id="additionalInformation">
              <Translate contentKey="eInvoicesApp.receiverAddress.additionalInformation">Additional Information</Translate>
            </span>
          </dt>
          <dd>{receiverAddressEntity.additionalInformation}</dd>
        </dl>
        <Button tag={Link} to="/receiver-address" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/receiver-address/${receiverAddressEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ReceiverAddressDetail;
