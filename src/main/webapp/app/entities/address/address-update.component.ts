import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAddress } from 'app/shared/model/address.model';
import { AddressService } from './address.service';
import { IContact } from 'app/shared/model/contact.model';
import { ContactService } from 'app/entities/contact';
import { IFormation } from 'app/shared/model/formation.model';
import { FormationService } from 'app/entities/formation';

@Component({
    selector: 'jhi-address-update',
    templateUrl: './address-update.component.html'
})
export class AddressUpdateComponent implements OnInit {
    address: IAddress;
    isSaving: boolean;

    contacts: IContact[];

    formations: IFormation[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected addressService: AddressService,
        protected contactService: ContactService,
        protected formationService: FormationService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ address }) => {
            this.address = address;
        });
        this.contactService
            .query({ filter: 'address-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IContact[]>) => mayBeOk.ok),
                map((response: HttpResponse<IContact[]>) => response.body)
            )
            .subscribe(
                (res: IContact[]) => {
                    if (!this.address.contact || !this.address.contact.id) {
                        this.contacts = res;
                    } else {
                        this.contactService
                            .find(this.address.contact.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IContact>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IContact>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IContact) => (this.contacts = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.formationService
            .query({ filter: 'address-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IFormation[]>) => mayBeOk.ok),
                map((response: HttpResponse<IFormation[]>) => response.body)
            )
            .subscribe(
                (res: IFormation[]) => {
                    if (!this.address.formation || !this.address.formation.id) {
                        this.formations = res;
                    } else {
                        this.formationService
                            .find(this.address.formation.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IFormation>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IFormation>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IFormation) => (this.formations = [subRes].concat(res)),
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
        if (this.address.id !== undefined) {
            this.subscribeToSaveResponse(this.addressService.update(this.address));
        } else {
            this.subscribeToSaveResponse(this.addressService.create(this.address));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAddress>>) {
        result.subscribe((res: HttpResponse<IAddress>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackFormationById(index: number, item: IFormation) {
        return item.id;
    }
}
