import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAboutMe } from 'app/shared/model/about-me.model';
import { AboutMeService } from './about-me.service';
import { IPersonal } from 'app/shared/model/personal.model';
import { PersonalService } from 'app/entities/personal';

@Component({
    selector: 'jhi-about-me-update',
    templateUrl: './about-me-update.component.html'
})
export class AboutMeUpdateComponent implements OnInit {
    aboutMe: IAboutMe;
    isSaving: boolean;

    personals: IPersonal[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected aboutMeService: AboutMeService,
        protected personalService: PersonalService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ aboutMe }) => {
            this.aboutMe = aboutMe;
        });
        this.personalService
            .query({ filter: 'aboutme-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IPersonal[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPersonal[]>) => response.body)
            )
            .subscribe(
                (res: IPersonal[]) => {
                    if (!this.aboutMe.personal || !this.aboutMe.personal.id) {
                        this.personals = res;
                    } else {
                        this.personalService
                            .find(this.aboutMe.personal.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IPersonal>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IPersonal>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IPersonal) => (this.personals = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.aboutMe.id !== undefined) {
            this.subscribeToSaveResponse(this.aboutMeService.update(this.aboutMe));
        } else {
            this.subscribeToSaveResponse(this.aboutMeService.create(this.aboutMe));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAboutMe>>) {
        result.subscribe((res: HttpResponse<IAboutMe>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
