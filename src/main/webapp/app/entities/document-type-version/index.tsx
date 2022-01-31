import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import DocumentTypeVersion from './document-type-version';
import DocumentTypeVersionDetail from './document-type-version-detail';
import DocumentTypeVersionUpdate from './document-type-version-update';
import DocumentTypeVersionDeleteDialog from './document-type-version-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DocumentTypeVersionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DocumentTypeVersionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DocumentTypeVersionDetail} />
      <ErrorBoundaryRoute path={match.url} component={DocumentTypeVersion} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={DocumentTypeVersionDeleteDialog} />
  </>
);

export default Routes;
