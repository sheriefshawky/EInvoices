import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './value.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ValueDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const valueEntity = useAppSelector(state => state.value.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="valueDetailsHeading">
          <Translate contentKey="eInvoicesApp.value.detail.title">Value</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{valueEntity.id}</dd>
          <dt>
            <span id="currencySold">
              <Translate contentKey="eInvoicesApp.value.currencySold">Currency Sold</Translate>
            </span>
          </dt>
          <dd>{valueEntity.currencySold}</dd>
          <dt>
            <span id="amountEGP">
              <Translate contentKey="eInvoicesApp.value.amountEGP">Amount EGP</Translate>
            </span>
          </dt>
          <dd>{valueEntity.amountEGP}</dd>
          <dt>
            <span id="amountSold">
              <Translate contentKey="eInvoicesApp.value.amountSold">Amount Sold</Translate>
            </span>
          </dt>
          <dd>{valueEntity.amountSold}</dd>
          <dt>
            <span id="currencyExchangeRate">
              <Translate contentKey="eInvoicesApp.value.currencyExchangeRate">Currency Exchange Rate</Translate>
            </span>
          </dt>
          <dd>{valueEntity.currencyExchangeRate}</dd>
        </dl>
        <Button tag={Link} to="/value" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/value/${valueEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ValueDetail;
