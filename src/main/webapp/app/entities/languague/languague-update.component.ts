import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ILanguague } from 'app/shared/model/languague.model';
import { LanguagueService } from './languague.service';
import { IPersonal } from 'app/shared/model/personal.model';
import { PersonalService } from 'app/entities/personal';

@Component({
    selector: 'jhi-languague-update',
    templateUrl: './languague-update.component.html'
})
export class LanguagueUpdateComponent implements OnInit {
    languague: ILanguague;
    isSaving: boolean;

    personals: IPersonal[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected languagueService: LanguagueService,
        protected personalService: PersonalService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ languague }) => {
            this.languague = languague;
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
        if (this.languague.id !== undefined) {
            this.subscribeToSaveResponse(this.languagueService.update(this.languague));
        } else {
            this.subscribeToSaveResponse(this.languagueService.create(this.languague));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILanguague>>) {
        result.subscribe((res: HttpResponse<ILanguague>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
