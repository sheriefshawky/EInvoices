import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IIssuerAddress } from 'app/shared/model/issuer-address.model';
import { getEntities as getIssuerAddresses } from 'app/entities/issuer-address/issuer-address.reducer';
import { getEntity, updateEntity, createEntity, reset } from './issuer.reducer';
import { IIssuer } from 'app/shared/model/issuer.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const IssuerUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const issuerAddresses = useAppSelector(state => state.issuerAddress.entities);
  const issuerEntity = useAppSelector(state => state.issuer.entity);
  const loading = useAppSelector(state => state.issuer.loading);
  const updating = useAppSelector(state => state.issuer.updating);
  const updateSuccess = useAppSelector(state => state.issuer.updateSuccess);
  const handleClose = () => {
    props.history.push('/issuer');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getIssuerAddresses({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...issuerEntity,
      ...values,
      address: issuerAddresses.find(it => it.id.toString() === values.address.toString()),
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
          ...issuerEntity,
          address: issuerEntity?.address?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eInvoicesApp.issuer.home.createOrEditLabel" data-cy="IssuerCreateUpdateHeading">
            <Translate contentKey="eInvoicesApp.issuer.home.createOrEditLabel">Create or edit a Issuer</Translate>
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
                  id="issuer-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eInvoicesApp.issuer.issuertype')}
                id="issuer-issuertype"
                name="issuertype"
                data-cy="issuertype"
                type="text"
              />
              <ValidatedField label={translate('eInvoicesApp.issuer.name')} id="issuer-name" name="name" data-cy="name" type="text" />
              <ValidatedField
                id="issuer-address"
                name="address"
                data-cy="address"
                label={translate('eInvoicesApp.issuer.address')}
                type="select"
              >
                <option value="" key="0" />
                {issuerAddresses
                  ? issuerAddresses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/issuer" replace color="info">
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

export default IssuerUpdate;
