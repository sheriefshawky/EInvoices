import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import DocumentType from './document-type';
import DocumentTypeVersion from './document-type-version';
import WorkflowParameters from './workflow-parameters';
import Document from './document';
import Issuer from './issuer';
import IssuerAddress from './issuer-address';
import Receiver from './receiver';
import ReceiverAddress from './receiver-address';
import Payment from './payment';
import Delivery from './delivery';
import InvoiceLine from './invoice-line';
import Value from './value';
import Discount from './discount';
import TaxableItem from './taxable-item';
import TaxTotal from './tax-total';
import Signature from './signature';
import ItemValue from './item-value';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}document-type`} component={DocumentType} />
      <ErrorBoundaryRoute path={`${match.url}document-type-version`} component={DocumentTypeVersion} />
      <ErrorBoundaryRoute path={`${match.url}workflow-parameters`} component={WorkflowParameters} />
      <ErrorBoundaryRoute path={`${match.url}document`} component={Document} />
      <ErrorBoundaryRoute path={`${match.url}issuer`} component={Issuer} />
      <ErrorBoundaryRoute path={`${match.url}issuer-address`} component={IssuerAddress} />
      <ErrorBoundaryRoute path={`${match.url}receiver`} component={Receiver} />
      <ErrorBoundaryRoute path={`${match.url}receiver-address`} component={ReceiverAddress} />
      <ErrorBoundaryRoute path={`${match.url}payment`} component={Payment} />
      <ErrorBoundaryRoute path={`${match.url}delivery`} component={Delivery} />
      <ErrorBoundaryRoute path={`${match.url}invoice-line`} component={InvoiceLine} />
      <ErrorBoundaryRoute path={`${match.url}value`} component={Value} />
      <ErrorBoundaryRoute path={`${match.url}discount`} component={Discount} />
      <ErrorBoundaryRoute path={`${match.url}taxable-item`} component={TaxableItem} />
      <ErrorBoundaryRoute path={`${match.url}tax-total`} component={TaxTotal} />
      <ErrorBoundaryRoute path={`${match.url}signature`} component={Signature} />
      <ErrorBoundaryRoute path={`${match.url}item-value`} component={ItemValue} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
