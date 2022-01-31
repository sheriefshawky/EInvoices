import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ReceiverAddress from './receiver-address';
import ReceiverAddressDetail from './receiver-address-detail';
import ReceiverAddressUpdate from './receiver-address-update';
import ReceiverAddressDeleteDialog from './receiver-address-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ReceiverAddressUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ReceiverAddressUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ReceiverAddressDetail} />
      <ErrorBoundaryRoute path={match.url} component={ReceiverAddress} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ReceiverAddressDeleteDialog} />
  </>
);

export default Routes;
