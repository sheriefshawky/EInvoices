import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './issuer-address.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const IssuerAddressDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const issuerAddressEntity = useAppSelector(state => state.issuerAddress.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="issuerAddressDetailsHeading">
          <Translate contentKey="eInvoicesApp.issuerAddress.detail.title">IssuerAddress</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{issuerAddressEntity.id}</dd>
          <dt>
            <span id="branchId">
              <Translate contentKey="eInvoicesApp.issuerAddress.branchId">Branch Id</Translate>
            </span>
          </dt>
          <dd>{issuerAddressEntity.branchId}</dd>
          <dt>
            <span id="country">
              <Translate contentKey="eInvoicesApp.issuerAddress.country">Country</Translate>
            </span>
          </dt>
          <dd>{issuerAddressEntity.country}</dd>
          <dt>
            <span id="governate">
              <Translate contentKey="eInvoicesApp.issuerAddress.governate">Governate</Translate>
            </span>
          </dt>
          <dd>{issuerAddressEntity.governate}</dd>
          <dt>
            <span id="regionCity">
              <Translate contentKey="eInvoicesApp.issuerAddress.regionCity">Region City</Translate>
            </span>
          </dt>
          <dd>{issuerAddressEntity.regionCity}</dd>
          <dt>
            <span id="street">
              <Translate contentKey="eInvoicesApp.issuerAddress.street">Street</Translate>
            </span>
          </dt>
          <dd>{issuerAddressEntity.street}</dd>
          <dt>
            <span id="buildingNumber">
              <Translate contentKey="eInvoicesApp.issuerAddress.buildingNumber">Building Number</Translate>
            </span>
          </dt>
          <dd>{issuerAddressEntity.buildingNumber}</dd>
          <dt>
            <span id="postalCode">
              <Translate contentKey="eInvoicesApp.issuerAddress.postalCode">Postal Code</Translate>
            </span>
          </dt>
          <dd>{issuerAddressEntity.postalCode}</dd>
          <dt>
            <span id="floor">
              <Translate contentKey="eInvoicesApp.issuerAddress.floor">Floor</Translate>
            </span>
          </dt>
          <dd>{issuerAddressEntity.floor}</dd>
          <dt>
            <span id="room">
              <Translate contentKey="eInvoicesApp.issuerAddress.room">Room</Translate>
            </span>
          </dt>
          <dd>{issuerAddressEntity.room}</dd>
          <dt>
            <span id="landmark">
              <Translate contentKey="eInvoicesApp.issuerAddress.landmark">Landmark</Translate>
            </span>
          </dt>
          <dd>{issuerAddressEntity.landmark}</dd>
          <dt>
            <span id="additionalInformation">
              <Translate contentKey="eInvoicesApp.issuerAddress.additionalInformation">Additional Information</Translate>
            </span>
          </dt>
          <dd>{issuerAddressEntity.additionalInformation}</dd>
        </dl>
        <Button tag={Link} to="/issuer-address" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/issuer-address/${issuerAddressEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default IssuerAddressDetail;
