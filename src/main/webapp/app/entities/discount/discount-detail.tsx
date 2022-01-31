import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './discount.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const DiscountDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const discountEntity = useAppSelector(state => state.discount.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="discountDetailsHeading">
          <Translate contentKey="eInvoicesApp.discount.detail.title">Discount</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{discountEntity.id}</dd>
          <dt>
            <span id="rate">
              <Translate contentKey="eInvoicesApp.discount.rate">Rate</Translate>
            </span>
          </dt>
          <dd>{discountEntity.rate}</dd>
          <dt>
            <span id="amount">
              <Translate contentKey="eInvoicesApp.discount.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{discountEntity.amount}</dd>
        </dl>
        <Button tag={Link} to="/discount" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/discount/${discountEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DiscountDetail;
