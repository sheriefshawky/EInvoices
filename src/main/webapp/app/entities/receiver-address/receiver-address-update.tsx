import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './receiver-address.reducer';
import { IReceiverAddress } from 'app/shared/model/receiver-address.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ReceiverAddressUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const receiverAddressEntity = useAppSelector(state => state.receiverAddress.entity);
  const loading = useAppSelector(state => state.receiverAddress.loading);
  const updating = useAppSelector(state => state.receiverAddress.updating);
  const updateSuccess = useAppSelector(state => state.receiverAddress.updateSuccess);
  const handleClose = () => {
    props.history.push('/receiver-address');
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
      ...receiverAddressEntity,
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
          ...receiverAddressEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eInvoicesApp.receiverAddress.home.createOrEditLabel" data-cy="ReceiverAddressCreateUpdateHeading">
            <Translate contentKey="eInvoicesApp.receiverAddress.home.createOrEditLabel">Create or edit a ReceiverAddress</Translate>
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
                  id="receiver-address-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eInvoicesApp.receiverAddress.country')}
                id="receiver-address-country"
                name="country"
                data-cy="country"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.receiverAddress.governate')}
                id="receiver-address-governate"
                name="governate"
                data-cy="governate"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.receiverAddress.regionCity')}
                id="receiver-address-regionCity"
                name="regionCity"
                data-cy="regionCity"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.receiverAddress.street')}
                id="receiver-address-street"
                name="street"
                data-cy="street"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.receiverAddress.buildingNumber')}
                id="receiver-address-buildingNumber"
                name="buildingNumber"
                data-cy="buildingNumber"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.receiverAddress.postalCode')}
                id="receiver-address-postalCode"
                name="postalCode"
                data-cy="postalCode"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.receiverAddress.floor')}
                id="receiver-address-floor"
                name="floor"
                data-cy="floor"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.receiverAddress.room')}
                id="receiver-address-room"
                name="room"
                data-cy="room"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.receiverAddress.landmark')}
                id="receiver-address-landmark"
                name="landmark"
                data-cy="landmark"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.receiverAddress.additionalInformation')}
                id="receiver-address-additionalInformation"
                name="additionalInformation"
                data-cy="additionalInformation"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/receiver-address" replace color="info">
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

export default ReceiverAddressUpdate;
