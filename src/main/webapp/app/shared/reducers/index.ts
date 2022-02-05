import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale from './locale';
import authentication from './authentication';
import applicationProfile from './application-profile';

import administration from 'app/modules/administration/administration.reducer';
import userManagement from 'app/modules/administration/user-management/user-management.reducer';
import register from 'app/modules/account/register/register.reducer';
import activate from 'app/modules/account/activate/activate.reducer';
import password from 'app/modules/account/password/password.reducer';
import settings from 'app/modules/account/settings/settings.reducer';
import passwordReset from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import documentType from 'app/entities/document-type/document-type.reducer';
// prettier-ignore
import documentTypeVersion from 'app/entities/document-type-version/document-type-version.reducer';
// prettier-ignore
import workflowParameters from 'app/entities/workflow-parameters/workflow-parameters.reducer';
// prettier-ignore
import document from 'app/entities/document/document.reducer';
// prettier-ignore
import issuer from 'app/entities/issuer/issuer.reducer';
// prettier-ignore
import issuerAddress from 'app/entities/issuer-address/issuer-address.reducer';
// prettier-ignore
import receiver from 'app/entities/receiver/receiver.reducer';
// prettier-ignore
import receiverAddress from 'app/entities/receiver-address/receiver-address.reducer';
// prettier-ignore
import payment from 'app/entities/payment/payment.reducer';
// prettier-ignore
import delivery from 'app/entities/delivery/delivery.reducer';
// prettier-ignore
import invoiceLine from 'app/entities/invoice-line/invoice-line.reducer';
// prettier-ignore
import value from 'app/entities/value/value.reducer';
// prettier-ignore
import discount from 'app/entities/discount/discount.reducer';
// prettier-ignore
import taxableItem from 'app/entities/taxable-item/taxable-item.reducer';
// prettier-ignore
import taxTotal from 'app/entities/tax-total/tax-total.reducer';
// prettier-ignore
import signature from 'app/entities/signature/signature.reducer';
// prettier-ignore
import itemValue from 'app/entities/item-value/item-value.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const rootReducer = {
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  documentType,
  documentTypeVersion,
  workflowParameters,
  document,
  issuer,
  issuerAddress,
  receiver,
  receiverAddress,
  payment,
  delivery,
  invoiceLine,
  value,
  discount,
  taxableItem,
  taxTotal,
  signature,
  itemValue,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
};

export default rootReducer;
