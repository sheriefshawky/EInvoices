import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './payment.reducer';
import { IPayment } from 'app/shared/model/payment.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Payment = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const paymentList = useAppSelector(state => state.payment.entities);
  const loading = useAppSelector(state => state.payment.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="payment-heading" data-cy="PaymentHeading">
        <Translate contentKey="eInvoicesApp.payment.home.title">Payments</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="eInvoicesApp.payment.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="eInvoicesApp.payment.home.createLabel">Create new Payment</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {paymentList && paymentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="eInvoicesApp.payment.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.payment.bankName">Bank Name</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.payment.bankAddress">Bank Address</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.payment.bankAccountNo">Bank Account No</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.payment.bankAccountIBAN">Bank Account IBAN</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.payment.swiftCode">Swift Code</Translate>
                </th>
                <th>
                  <Translate contentKey="eInvoicesApp.payment.terms">Terms</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {paymentList.map((payment, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${payment.id}`} color="link" size="sm">
                      {payment.id}
                    </Button>
                  </td>
                  <td>{payment.bankName}</td>
                  <td>{payment.bankAddress}</td>
                  <td>{payment.bankAccountNo}</td>
                  <td>{payment.bankAccountIBAN}</td>
                  <td>{payment.swiftCode}</td>
                  <td>{payment.terms}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${payment.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${payment.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${payment.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="eInvoicesApp.payment.home.notFound">No Payments found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Payment;
