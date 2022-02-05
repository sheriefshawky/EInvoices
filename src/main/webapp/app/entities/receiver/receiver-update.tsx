import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IReceiverAddress } from 'app/shared/model/receiver-address.model';
import { getEntities as getReceiverAddresses } from 'app/entities/receiver-address/receiver-address.reducer';
import { getEntity, updateEntity, createEntity, reset } from './receiver.reducer';
import { IReceiver } from 'app/shared/model/receiver.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ReceiverUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const receiverAddresses = useAppSelector(state => state.receiverAddress.entities);
  const receiverEntity = useAppSelector(state => state.receiver.entity);
  const loading = useAppSelector(state => state.receiver.loading);
  const updating = useAppSelector(state => state.receiver.updating);
  const updateSuccess = useAppSelector(state => state.receiver.updateSuccess);
  const handleClose = () => {
    props.history.push('/receiver');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getReceiverAddresses({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...receiverEntity,
      ...values,
      address: receiverAddresses.find(it => it.id.toString() === values.address.toString()),
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
          ...receiverEntity,
          address: receiverEntity?.address?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eInvoicesApp.receiver.home.createOrEditLabel" data-cy="ReceiverCreateUpdateHeading">
            <Translate contentKey="eInvoicesApp.receiver.home.createOrEditLabel">Create or edit a Receiver</Translate>
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
                  id="receiver-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('eInvoicesApp.receiver.recieverType')}
                id="receiver-recieverType"
                name="recieverType"
                data-cy="recieverType"
                type="text"
              />
              <ValidatedField label={translate('eInvoicesApp.receiver.name')} id="receiver-name" name="name" data-cy="name" type="text" />
              <ValidatedField
                id="receiver-address"
                name="address"
                data-cy="address"
                label={translate('eInvoicesApp.receiver.address')}
                type="select"
              >
                <option value="" key="0" />
                {receiverAddresses
                  ? receiverAddresses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/receiver" replace color="info">
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

export default ReceiverUpdate;
