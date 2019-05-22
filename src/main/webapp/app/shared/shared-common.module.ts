import { NgModule } from '@angular/core';

import { SharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [SharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [SharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class SharedCommonModule {}
