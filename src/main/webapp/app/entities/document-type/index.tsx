import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import DocumentType from './document-type';
import DocumentTypeDetail from './document-type-detail';
import DocumentTypeUpdate from './document-type-update';
import DocumentTypeDeleteDialog from './document-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DocumentTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DocumentTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DocumentTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={DocumentType} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={DocumentTypeDeleteDialog} />
  </>
);

export default Routes;
