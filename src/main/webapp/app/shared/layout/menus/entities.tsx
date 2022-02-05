import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <>{/* to avoid warnings when empty */}</>
    <MenuItem icon="asterisk" to="/document-type">
      <Translate contentKey="global.menu.entities.documentType" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/document-type-version">
      <Translate contentKey="global.menu.entities.documentTypeVersion" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/workflow-parameters">
      <Translate contentKey="global.menu.entities.workflowParameters" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/document">
      <Translate contentKey="global.menu.entities.document" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/issuer">
      <Translate contentKey="global.menu.entities.issuer" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/issuer-address">
      <Translate contentKey="global.menu.entities.issuerAddress" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/receiver">
      <Translate contentKey="global.menu.entities.receiver" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/receiver-address">
      <Translate contentKey="global.menu.entities.receiverAddress" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/payment">
      <Translate contentKey="global.menu.entities.payment" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/delivery">
      <Translate contentKey="global.menu.entities.delivery" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/invoice-line">
      <Translate contentKey="global.menu.entities.invoiceLine" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/value">
      <Translate contentKey="global.menu.entities.value" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/discount">
      <Translate contentKey="global.menu.entities.discount" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/taxable-item">
      <Translate contentKey="global.menu.entities.taxableItem" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/tax-total">
      <Translate contentKey="global.menu.entities.taxTotal" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/signature">
      <Translate contentKey="global.menu.entities.signature" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/item-value">
      <Translate contentKey="global.menu.entities.itemValue" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
