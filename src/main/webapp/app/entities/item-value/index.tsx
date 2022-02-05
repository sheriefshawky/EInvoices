import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ItemValue from './item-value';
import ItemValueDetail from './item-value-detail';
import ItemValueUpdate from './item-value-update';
import ItemValueDeleteDialog from './item-value-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ItemValueUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ItemValueUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ItemValueDetail} />
      <ErrorBoundaryRoute path={match.url} component={ItemValue} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ItemValueDeleteDialog} />
  </>
);

export default Routes;
