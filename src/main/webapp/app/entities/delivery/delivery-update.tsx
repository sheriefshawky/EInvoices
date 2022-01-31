import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './delivery.reducer';
import { IDelivery } from 'app/shared/model/delivery.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const DeliveryUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const deliveryEntity = useAppSelector(state => state.delivery.entity);
  const loading = useAppSelector(state => state.delivery.loading);
  const updating = useAppSelector(state => state.delivery.updating);
  const updateSuccess = useAppSelector(state => state.delivery.updateSuccess);
  const handleClose = () => {
    props.history.push('/delivery');
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
      ...deliveryEntity,
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
          ...deliveryEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eInvoicesApp.delivery.home.createOrEditLabel" data-cy="DeliveryCreateUpdateHeading">
            <Translate contentKey="eInvoicesApp.delivery.home.createOrEditLabel">Create or edit a Delivery</Translate>
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
                  id="delivery-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eInvoicesApp.delivery.approach')}
                id="delivery-approach"
                name="approach"
                data-cy="approach"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.delivery.packaging')}
                id="delivery-packaging"
                name="packaging"
                data-cy="packaging"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.delivery.dateValidity')}
                id="delivery-dateValidity"
                name="dateValidity"
                data-cy="dateValidity"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.delivery.exportPort')}
                id="delivery-exportPort"
                name="exportPort"
                data-cy="exportPort"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.delivery.countryOfOrigin')}
                id="delivery-countryOfOrigin"
                name="countryOfOrigin"
                data-cy="countryOfOrigin"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.delivery.grossWeight')}
                id="delivery-grossWeight"
                name="grossWeight"
                data-cy="grossWeight"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.delivery.netWeight')}
                id="delivery-netWeight"
                name="netWeight"
                data-cy="netWeight"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.delivery.terms')}
                id="delivery-terms"
                name="terms"
                data-cy="terms"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/delivery" replace color="info">
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

export default DeliveryUpdate;
