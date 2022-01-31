import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './tax-total.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TaxTotalDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const taxTotalEntity = useAppSelector(state => state.taxTotal.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="taxTotalDetailsHeading">
          <Translate contentKey="eInvoicesApp.taxTotal.detail.title">TaxTotal</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{taxTotalEntity.id}</dd>
          <dt>
            <span id="taxType">
              <Translate contentKey="eInvoicesApp.taxTotal.taxType">Tax Type</Translate>
            </span>
          </dt>
          <dd>{taxTotalEntity.taxType}</dd>
          <dt>
            <span id="amount">
              <Translate contentKey="eInvoicesApp.taxTotal.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{taxTotalEntity.amount}</dd>
          <dt>
            <Translate contentKey="eInvoicesApp.taxTotal.document">Document</Translate>
          </dt>
          <dd>{taxTotalEntity.document ? taxTotalEntity.document.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/tax-total" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tax-total/${taxTotalEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TaxTotalDetail;
