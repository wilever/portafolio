import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IFormation } from 'app/shared/model/formation.model';
import { FormationService } from './formation.service';
import { IPersonal } from 'app/shared/model/personal.model';
import { PersonalService } from 'app/entities/personal';

@Component({
    selector: 'jhi-formation-update',
    templateUrl: './formation-update.component.html'
})
export class FormationUpdateComponent implements OnInit {
    formation: IFormation;
    isSaving: boolean;

    personals: IPersonal[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected formationService: FormationService,
        protected personalService: PersonalService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ formation }) => {
            this.formation = formation;
        });
        this.personalService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPersonal[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPersonal[]>) => response.body)
            )
            .subscribe((res: IPersonal[]) => (this.personals = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.formation.id !== undefined) {
            this.subscribeToSaveResponse(this.formationService.update(this.formation));
        } else {
            this.subscribeToSaveResponse(this.formationService.create(this.formation));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IFormation>>) {
        result.subscribe((res: HttpResponse<IFormation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
