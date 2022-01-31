import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import IssuerAddress from './issuer-address';
import IssuerAddressDetail from './issuer-address-detail';
import IssuerAddressUpdate from './issuer-address-update';
import IssuerAddressDeleteDialog from './issuer-address-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={IssuerAddressUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={IssuerAddressUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={IssuerAddressDetail} />
      <ErrorBoundaryRoute path={match.url} component={IssuerAddress} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={IssuerAddressDeleteDialog} />
  </>
);

export default Routes;
