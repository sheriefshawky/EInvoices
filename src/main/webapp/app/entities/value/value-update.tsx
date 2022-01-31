import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './value.reducer';
import { IValue } from 'app/shared/model/value.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ValueUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const valueEntity = useAppSelector(state => state.value.entity);
  const loading = useAppSelector(state => state.value.loading);
  const updating = useAppSelector(state => state.value.updating);
  const updateSuccess = useAppSelector(state => state.value.updateSuccess);
  const handleClose = () => {
    props.history.push('/value');
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
      ...valueEntity,
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
          ...valueEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eInvoicesApp.value.home.createOrEditLabel" data-cy="ValueCreateUpdateHeading">
            <Translate contentKey="eInvoicesApp.value.home.createOrEditLabel">Create or edit a Value</Translate>
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
                  id="value-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eInvoicesApp.value.currencySold')}
                id="value-currencySold"
                name="currencySold"
                data-cy="currencySold"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.value.amountEGP')}
                id="value-amountEGP"
                name="amountEGP"
                data-cy="amountEGP"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.value.amountSold')}
                id="value-amountSold"
                name="amountSold"
                data-cy="amountSold"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.value.currencyExchangeRate')}
                id="value-currencyExchangeRate"
                name="currencyExchangeRate"
                data-cy="currencyExchangeRate"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/value" replace color="info">
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

export default ValueUpdate;
