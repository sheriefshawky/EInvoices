import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './item-value.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ItemValueDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const itemValueEntity = useAppSelector(state => state.itemValue.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="itemValueDetailsHeading">
          <Translate contentKey="eInvoicesApp.itemValue.detail.title">ItemValue</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{itemValueEntity.id}</dd>
          <dt>
            <span id="currencySold">
              <Translate contentKey="eInvoicesApp.itemValue.currencySold">Currency Sold</Translate>
            </span>
          </dt>
          <dd>{itemValueEntity.currencySold}</dd>
          <dt>
            <span id="amountEGP">
              <Translate contentKey="eInvoicesApp.itemValue.amountEGP">Amount EGP</Translate>
            </span>
          </dt>
          <dd>{itemValueEntity.amountEGP}</dd>
          <dt>
            <span id="amountSold">
              <Translate contentKey="eInvoicesApp.itemValue.amountSold">Amount Sold</Translate>
            </span>
          </dt>
          <dd>{itemValueEntity.amountSold}</dd>
          <dt>
            <span id="currencyExchangeRate">
              <Translate contentKey="eInvoicesApp.itemValue.currencyExchangeRate">Currency Exchange Rate</Translate>
            </span>
          </dt>
          <dd>{itemValueEntity.currencyExchangeRate}</dd>
        </dl>
        <Button tag={Link} to="/item-value" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/item-value/${itemValueEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ItemValueDetail;
