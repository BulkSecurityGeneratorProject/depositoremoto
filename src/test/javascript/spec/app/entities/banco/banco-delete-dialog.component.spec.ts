/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TestModule } from '../../../test.module';
import { BancoDeleteDialogComponent } from 'app/entities/banco/banco-delete-dialog.component';
import { BancoService } from 'app/entities/banco/banco.service';

describe('Component Tests', () => {
  describe('Banco Management Delete Component', () => {
    let comp: BancoDeleteDialogComponent;
    let fixture: ComponentFixture<BancoDeleteDialogComponent>;
    let service: BancoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestModule],
        declarations: [BancoDeleteDialogComponent]
      })
        .overrideTemplate(BancoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BancoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BancoService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});