import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IBanco, Banco } from 'app/shared/model/banco.model';
import { BancoService } from './banco.service';

@Component({
  selector: 'jhi-banco-update',
  templateUrl: './banco-update.component.html'
})
export class BancoUpdateComponent implements OnInit {
  banco: IBanco;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nombreDeFantasia: [],
    numero: [],
    razonSocial: [],
    cuit: [],
    otrosDatos: [],
    activo: [],
    diferidos: []
  });

  constructor(protected bancoService: BancoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ banco }) => {
      this.updateForm(banco);
      this.banco = banco;
    });
  }

  updateForm(banco: IBanco) {
    this.editForm.patchValue({
      id: banco.id,
      nombreDeFantasia: banco.nombreDeFantasia,
      numero: banco.numero,
      razonSocial: banco.razonSocial,
      cuit: banco.cuit,
      otrosDatos: banco.otrosDatos,
      activo: banco.activo,
      diferidos: banco.diferidos
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const banco = this.createFromForm();
    if (banco.id !== undefined) {
      this.subscribeToSaveResponse(this.bancoService.update(banco));
    } else {
      this.subscribeToSaveResponse(this.bancoService.create(banco));
    }
  }

  private createFromForm(): IBanco {
    const entity = {
      ...new Banco(),
      id: this.editForm.get(['id']).value,
      nombreDeFantasia: this.editForm.get(['nombreDeFantasia']).value,
      numero: this.editForm.get(['numero']).value,
      razonSocial: this.editForm.get(['razonSocial']).value,
      cuit: this.editForm.get(['cuit']).value,
      otrosDatos: this.editForm.get(['otrosDatos']).value,
      activo: this.editForm.get(['activo']).value,
      diferidos: this.editForm.get(['diferidos']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBanco>>) {
    result.subscribe((res: HttpResponse<IBanco>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
