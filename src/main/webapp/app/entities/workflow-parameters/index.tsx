import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import WorkflowParameters from './workflow-parameters';
import WorkflowParametersDetail from './workflow-parameters-detail';
import WorkflowParametersUpdate from './workflow-parameters-update';
import WorkflowParametersDeleteDialog from './workflow-parameters-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={WorkflowParametersUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={WorkflowParametersUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={WorkflowParametersDetail} />
      <ErrorBoundaryRoute path={match.url} component={WorkflowParameters} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={WorkflowParametersDeleteDialog} />
  </>
);

export default Routes;
