import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TaxableItem from './taxable-item';
import TaxableItemDetail from './taxable-item-detail';
import TaxableItemUpdate from './taxable-item-update';
import TaxableItemDeleteDialog from './taxable-item-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TaxableItemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TaxableItemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TaxableItemDetail} />
      <ErrorBoundaryRoute path={match.url} component={TaxableItem} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TaxableItemDeleteDialog} />
  </>
);

export default Routes;
