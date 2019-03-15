import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IExperience } from 'app/shared/model/experience.model';
import { ExperienceService } from './experience.service';
import { IPersonal } from 'app/shared/model/personal.model';
import { PersonalService } from 'app/entities/personal';

@Component({
    selector: 'jhi-experience-update',
    templateUrl: './experience-update.component.html'
})
export class ExperienceUpdateComponent implements OnInit {
    experience: IExperience;
    isSaving: boolean;

    personals: IPersonal[];
    startDateDp: any;
    endDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected experienceService: ExperienceService,
        protected personalService: PersonalService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ experience }) => {
            this.experience = experience;
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
        if (this.experience.id !== undefined) {
            this.subscribeToSaveResponse(this.experienceService.update(this.experience));
        } else {
            this.subscribeToSaveResponse(this.experienceService.create(this.experience));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IExperience>>) {
        result.subscribe((res: HttpResponse<IExperience>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
