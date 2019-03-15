import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IHobby } from 'app/shared/model/hobby.model';
import { HobbyService } from './hobby.service';
import { IPersonal } from 'app/shared/model/personal.model';
import { PersonalService } from 'app/entities/personal';

@Component({
    selector: 'jhi-hobby-update',
    templateUrl: './hobby-update.component.html'
})
export class HobbyUpdateComponent implements OnInit {
    hobby: IHobby;
    isSaving: boolean;

    personals: IPersonal[];

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected hobbyService: HobbyService,
        protected personalService: PersonalService,
        protected elementRef: ElementRef,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ hobby }) => {
            this.hobby = hobby;
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
        this.dataUtils.clearInputImage(this.hobby, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.hobby.id !== undefined) {
            this.subscribeToSaveResponse(this.hobbyService.update(this.hobby));
        } else {
            this.subscribeToSaveResponse(this.hobbyService.create(this.hobby));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IHobby>>) {
        result.subscribe((res: HttpResponse<IHobby>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
