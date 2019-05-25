import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared';
import {
  BancoComponent,
  BancoDetailComponent,
  BancoUpdateComponent,
  BancoDeletePopupComponent,
  BancoDeleteDialogComponent,
  bancoRoute,
  bancoPopupRoute
} from './';

const ENTITY_STATES = [...bancoRoute, ...bancoPopupRoute];

@NgModule({
  imports: [SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [BancoComponent, BancoDetailComponent, BancoUpdateComponent, BancoDeleteDialogComponent, BancoDeletePopupComponent],
  entryComponents: [BancoComponent, BancoUpdateComponent, BancoDeleteDialogComponent, BancoDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BancoModule {}
