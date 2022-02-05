import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './item-value.reducer';
import { IItemValue } from 'app/shared/model/item-value.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ItemValueUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const itemValueEntity = useAppSelector(state => state.itemValue.entity);
  const loading = useAppSelector(state => state.itemValue.loading);
  const updating = useAppSelector(state => state.itemValue.updating);
  const updateSuccess = useAppSelector(state => state.itemValue.updateSuccess);
  const handleClose = () => {
    props.history.push('/item-value');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...itemValueEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...itemValueEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eInvoicesApp.itemValue.home.createOrEditLabel" data-cy="ItemValueCreateUpdateHeading">
            <Translate contentKey="eInvoicesApp.itemValue.home.createOrEditLabel">Create or edit a ItemValue</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="item-value-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eInvoicesApp.itemValue.currencySold')}
                id="item-value-currencySold"
                name="currencySold"
                data-cy="currencySold"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.itemValue.amountEGP')}
                id="item-value-amountEGP"
                name="amountEGP"
                data-cy="amountEGP"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.itemValue.amountSold')}
                id="item-value-amountSold"
                name="amountSold"
                data-cy="amountSold"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.itemValue.currencyExchangeRate')}
                id="item-value-currencyExchangeRate"
                name="currencyExchangeRate"
                data-cy="currencyExchangeRate"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/item-value" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ItemValueUpdate;
