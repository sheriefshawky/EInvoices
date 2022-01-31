import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './payment.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PaymentDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const paymentEntity = useAppSelector(state => state.payment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="paymentDetailsHeading">
          <Translate contentKey="eInvoicesApp.payment.detail.title">Payment</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.id}</dd>
          <dt>
            <span id="bankName">
              <Translate contentKey="eInvoicesApp.payment.bankName">Bank Name</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.bankName}</dd>
          <dt>
            <span id="bankAddress">
              <Translate contentKey="eInvoicesApp.payment.bankAddress">Bank Address</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.bankAddress}</dd>
          <dt>
            <span id="bankAccountNo">
              <Translate contentKey="eInvoicesApp.payment.bankAccountNo">Bank Account No</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.bankAccountNo}</dd>
          <dt>
            <span id="bankAccountIBAN">
              <Translate contentKey="eInvoicesApp.payment.bankAccountIBAN">Bank Account IBAN</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.bankAccountIBAN}</dd>
          <dt>
            <span id="swiftCode">
              <Translate contentKey="eInvoicesApp.payment.swiftCode">Swift Code</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.swiftCode}</dd>
          <dt>
            <span id="terms">
              <Translate contentKey="eInvoicesApp.payment.terms">Terms</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.terms}</dd>
        </dl>
        <Button tag={Link} to="/payment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/payment/${paymentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PaymentDetail;
