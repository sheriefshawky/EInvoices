import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './issuer-address.reducer';
import { IIssuerAddress } from 'app/shared/model/issuer-address.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const IssuerAddressUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const issuerAddressEntity = useAppSelector(state => state.issuerAddress.entity);
  const loading = useAppSelector(state => state.issuerAddress.loading);
  const updating = useAppSelector(state => state.issuerAddress.updating);
  const updateSuccess = useAppSelector(state => state.issuerAddress.updateSuccess);
  const handleClose = () => {
    props.history.push('/issuer-address');
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
      ...issuerAddressEntity,
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
          ...issuerAddressEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eInvoicesApp.issuerAddress.home.createOrEditLabel" data-cy="IssuerAddressCreateUpdateHeading">
            <Translate contentKey="eInvoicesApp.issuerAddress.home.createOrEditLabel">Create or edit a IssuerAddress</Translate>
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
                  id="issuer-address-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eInvoicesApp.issuerAddress.branchId')}
                id="issuer-address-branchId"
                name="branchId"
                data-cy="branchId"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.issuerAddress.country')}
                id="issuer-address-country"
                name="country"
                data-cy="country"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.issuerAddress.governate')}
                id="issuer-address-governate"
                name="governate"
                data-cy="governate"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.issuerAddress.regionCity')}
                id="issuer-address-regionCity"
                name="regionCity"
                data-cy="regionCity"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.issuerAddress.street')}
                id="issuer-address-street"
                name="street"
                data-cy="street"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.issuerAddress.buildingNumber')}
                id="issuer-address-buildingNumber"
                name="buildingNumber"
                data-cy="buildingNumber"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.issuerAddress.postalCode')}
                id="issuer-address-postalCode"
                name="postalCode"
                data-cy="postalCode"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.issuerAddress.floor')}
                id="issuer-address-floor"
                name="floor"
                data-cy="floor"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.issuerAddress.room')}
                id="issuer-address-room"
                name="room"
                data-cy="room"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.issuerAddress.landmark')}
                id="issuer-address-landmark"
                name="landmark"
                data-cy="landmark"
                type="text"
              />
              <ValidatedField
                label={translate('eInvoicesApp.issuerAddress.additionalInformation')}
                id="issuer-address-additionalInformation"
                name="additionalInformation"
                data-cy="additionalInformation"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/issuer-address" replace color="info">
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

export default IssuerAddressUpdate;
