import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Value from './value';
import ValueDetail from './value-detail';
import ValueUpdate from './value-update';
import ValueDeleteDialog from './value-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ValueUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ValueUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ValueDetail} />
      <ErrorBoundaryRoute path={match.url} component={Value} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ValueDeleteDialog} />
  </>
);

export default Routes;
