import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ISocialAccount } from 'app/shared/model/social-account.model';
import { SocialAccountService } from './social-account.service';
import { IContact } from 'app/shared/model/contact.model';
import { ContactService } from 'app/entities/contact';

@Component({
    selector: 'jhi-social-account-update',
    templateUrl: './social-account-update.component.html'
})
export class SocialAccountUpdateComponent implements OnInit {
    socialAccount: ISocialAccount;
    isSaving: boolean;

    contacts: IContact[];

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected socialAccountService: SocialAccountService,
        protected contactService: ContactService,
        protected elementRef: ElementRef,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ socialAccount }) => {
            this.socialAccount = socialAccount;
        });
        this.contactService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IContact[]>) => mayBeOk.ok),
                map((response: HttpResponse<IContact[]>) => response.body)
            )
            .subscribe((res: IContact[]) => (this.contacts = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        this.dataUtils.clearInputImage(this.socialAccount, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.socialAccount.id !== undefined) {
            this.subscribeToSaveResponse(this.socialAccountService.update(this.socialAccount));
        } else {
            this.subscribeToSaveResponse(this.socialAccountService.create(this.socialAccount));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISocialAccount>>) {
        result.subscribe((res: HttpResponse<ISocialAccount>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackContactById(index: number, item: IContact) {
        return item.id;
    }
}
