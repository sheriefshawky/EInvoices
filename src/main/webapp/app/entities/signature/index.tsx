import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Signature from './signature';
import SignatureDetail from './signature-detail';
import SignatureUpdate from './signature-update';
import SignatureDeleteDialog from './signature-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SignatureUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SignatureUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SignatureDetail} />
      <ErrorBoundaryRoute path={match.url} component={Signature} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SignatureDeleteDialog} />
  </>
);

export default Routes;
