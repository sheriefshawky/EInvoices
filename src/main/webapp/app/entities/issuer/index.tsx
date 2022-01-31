import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Issuer from './issuer';
import IssuerDetail from './issuer-detail';
import IssuerUpdate from './issuer-update';
import IssuerDeleteDialog from './issuer-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={IssuerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={IssuerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={IssuerDetail} />
      <ErrorBoundaryRoute path={match.url} component={Issuer} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={IssuerDeleteDialog} />
  </>
);

export default Routes;
