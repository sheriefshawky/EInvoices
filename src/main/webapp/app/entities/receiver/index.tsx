import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Receiver from './receiver';
import ReceiverDetail from './receiver-detail';
import ReceiverUpdate from './receiver-update';
import ReceiverDeleteDialog from './receiver-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ReceiverUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ReceiverUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ReceiverDetail} />
      <ErrorBoundaryRoute path={match.url} component={Receiver} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ReceiverDeleteDialog} />
  </>
);

export default Routes;
