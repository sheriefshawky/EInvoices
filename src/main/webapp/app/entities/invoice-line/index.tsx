import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import InvoiceLine from './invoice-line';
import InvoiceLineDetail from './invoice-line-detail';
import InvoiceLineUpdate from './invoice-line-update';
import InvoiceLineDeleteDialog from './invoice-line-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={InvoiceLineUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={InvoiceLineUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={InvoiceLineDetail} />
      <ErrorBoundaryRoute path={match.url} component={InvoiceLine} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={InvoiceLineDeleteDialog} />
  </>
);

export default Routes;
