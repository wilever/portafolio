import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IHability } from 'app/shared/model/hability.model';
import { HabilityService } from './hability.service';
import { IPersonal } from 'app/shared/model/personal.model';
import { PersonalService } from 'app/entities/personal';

@Component({
    selector: 'jhi-hability-update',
    templateUrl: './hability-update.component.html'
})
export class HabilityUpdateComponent implements OnInit {
    hability: IHability;
    isSaving: boolean;

    personals: IPersonal[];

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected habilityService: HabilityService,
        protected personalService: PersonalService,
        protected elementRef: ElementRef,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ hability }) => {
            this.hability = hability;
        });
        this.personalService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPersonal[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPersonal[]>) => response.body)
            )
            .subscribe((res: IPersonal[]) => (this.personals = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.hability, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.hability.id !== undefined) {
            this.subscribeToSaveResponse(this.habilityService.update(this.hability));
        } else {
            this.subscribeToSaveResponse(this.habilityService.create(this.hability));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IHability>>) {
        result.subscribe((res: HttpResponse<IHability>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackPersonalById(index: number, item: IPersonal) {
        return item.id;
    }
}
