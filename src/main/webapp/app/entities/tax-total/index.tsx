import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TaxTotal from './tax-total';
import TaxTotalDetail from './tax-total-detail';
import TaxTotalUpdate from './tax-total-update';
import TaxTotalDeleteDialog from './tax-total-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TaxTotalUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TaxTotalUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TaxTotalDetail} />
      <ErrorBoundaryRoute path={match.url} component={TaxTotal} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TaxTotalDeleteDialog} />
  </>
);

export default Routes;
